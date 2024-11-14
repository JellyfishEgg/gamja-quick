package com.sparta.gamjaquick.user.service;

import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.user.dto.request.UserSearchParameter;
import com.sparta.gamjaquick.user.entity.RoleType;
import com.sparta.gamjaquick.user.entity.User;
import com.sparta.gamjaquick.user.repository.UserRepository;
import com.sparta.gamjaquick.user.dto.request.UserSignUpRequestDto;
import com.sparta.gamjaquick.user.dto.request.UserUpdateRequestDto;
import com.sparta.gamjaquick.user.dto.response.UserResponseDto;
import com.sparta.gamjaquick.user.dto.response.UserDeleteResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.sparta.gamjaquick.common.response.MessageType;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto registerUser(UserSignUpRequestDto signUpDto) {
        User user = new User(
                null,
                signUpDto.getUsername(),
                signUpDto.getNickname(),
                signUpDto.getEmail(),
                signUpDto.getPassword(),
                signUpDto.getPhoneNumber(),
                RoleType.CUSTOMER,
                true,
                false,
                null,
                null,
                null
        );
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new UserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto updateUser(Long id, UserUpdateRequestDto updateDto) {
        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setNickname(updateDto.getNickname());
        user.setEmail(updateDto.getEmail());
        user.setPhoneNumber(updateDto.getPhoneNumber());
        user.setIsPublic(updateDto.getIsPublic());
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    @Override
    public UserDeleteResponseDto deleteUser(Long id, String deletedBy) {
        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setIsDeleted(true);
        user.setDeletedBy(deletedBy);
        userRepository.save(user);
        return new UserDeleteResponseDto(id, deletedBy);
    }

    @Override
    public ApiResponseDto searchUsers(UserSearchParameter searchParam, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return ApiResponseDto.success(MessageType.RETRIEVE, userPage);
    }
}