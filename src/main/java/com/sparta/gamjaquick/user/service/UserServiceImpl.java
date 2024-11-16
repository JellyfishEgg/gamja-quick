package com.sparta.gamjaquick.user.service;

import com.sparta.gamjaquick.user.dto.request.UserSearchParameter;
import com.sparta.gamjaquick.user.entity.RoleType;
import com.sparta.gamjaquick.user.entity.User;
import com.sparta.gamjaquick.user.repository.UserRepository;
import com.sparta.gamjaquick.user.dto.request.UserSignUpRequestDto;
import com.sparta.gamjaquick.user.dto.request.UserUpdateRequestDto;
import com.sparta.gamjaquick.user.dto.response.UserResponseDto;
import com.sparta.gamjaquick.user.dto.response.UserDeleteResponseDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.gamjaquick.user.entity.RoleType.CUSTOMER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Enumerated(EnumType.STRING)
    private RoleType role;

    /**
     * 회원가입
     * @param signUpDto 회원가입 요청 데이터
     * @return UserResponseDto 생성된 사용자 정보
     */
    @Override
    public UserResponseDto registerUser(UserSignUpRequestDto signUpDto) {
        User user = User.builder()
                .username(signUpDto.getUsername())
                .nickname(signUpDto.getNickname())
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .phoneNumber(signUpDto.getPhoneNumber())
                .role(RoleType.CUSTOMER)
                .isPublic(true)
                .isDeleted(false)
                .build();

        userRepository.save(user);
        return new UserResponseDto(user);
    }

    /**
     * 특정 사용자 조회
     * @param id 조회할 사용자 ID
     * @return UserResponseDto 사용자 정보
     */
    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new UserResponseDto(user);
    }

    /**
     * 전체 사용자 조회
     * @return List<UserResponseDto> 모든 사용자 정보
     */
    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 사용자 정보 수정
     * @param id 수정할 사용자 ID
     * @param updateDto 수정할 데이터
     * @return UserResponseDto 수정된 사용자 정보
     */
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

    /**
     * 사용자 삭제
     * @param id 삭제할 사용자 ID
     * @param deletedBy 삭제자
     * @return UserDeleteResponseDto 삭제된 사용자 정보
     */
    @Override
    public UserDeleteResponseDto deleteUser(Long id, String deletedBy) {
        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setIsDeleted(true);
        user.setDeletedBy(deletedBy);
        user.setDeletedAt(java.time.LocalDateTime.now());

        userRepository.save(user);
        return new UserDeleteResponseDto(id, deletedBy);
    }

    /**
     * 사용자 검색 및 페이징 처리
     * @param searchParam 검색 조건
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return Page<User> 검색된 사용자 리스트
     */
    @Override
    public Page<User> searchUsers(UserSearchParameter searchParam, int page, int size) {
        // 정렬기준과 정렬방향 설정
        PageRequest pageable = PageRequest.of(page, size, searchParam.getSortDirection(), searchParam.getSortBy());

        // 사용자 검색 조건을 통해 페이징된 결과를 반환
        return userRepository.findByUsernameContainingOrEmailContainingOrPhoneNumberContaining(
                searchParam.getUsername(),
                searchParam.getEmail(),
                searchParam.getPhoneNumber(),
                pageable
        );
    }
}