package com.sparta.gamjaquick.user.service;

import com.sparta.gamjaquick.config.security.jwt.JwtProvider;
import com.sparta.gamjaquick.user.dto.request.UserLoginRequestDto;
import com.sparta.gamjaquick.user.dto.request.UserSearchParameter;
import com.sparta.gamjaquick.user.dto.response.UserLoginResponseDto;
import com.sparta.gamjaquick.user.entity.User;
import com.sparta.gamjaquick.user.repository.UserRepository;
import com.sparta.gamjaquick.user.dto.request.UserSignUpRequestDto;
import com.sparta.gamjaquick.user.dto.request.UserUpdateRequestDto;
import com.sparta.gamjaquick.user.dto.response.UserResponseDto;
import com.sparta.gamjaquick.user.dto.response.UserDeleteResponseDto;
import com.sparta.gamjaquick.user.entity.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    /**
     * 회원가입
     * @param signUpDto 회원가입 요청 데이터
     * @return UserResponseDto 생성된 사용자 정보
     */
    @Override
    public UserResponseDto registerUser(UserSignUpRequestDto signUpDto) {
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        User user = User.builder()
                .username(signUpDto.getUsername())
                .nickname(signUpDto.getNickname())
                .email(signUpDto.getEmail())
                .password(encodedPassword)
                .phoneNumber(signUpDto.getPhoneNumber())
                .role(RoleType.CUSTOMER)
                .isPublic(true)
                .isDeleted(false)
                .createdBy(signUpDto.getUsername())
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
     * @param username 인증된 사용자 이름
     * @param updateDto 수정할 데이터
     * @return UserResponseDto 수정된 사용자 정보
     */
    @Override
    public UserResponseDto updateUser(String username, UserUpdateRequestDto updateDto) {
        User user = userRepository.findByUsername(username)
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
     * @param username 인증된 사용자 이름
     * @return UserDeleteResponseDto 삭제된 사용자 정보
     */
    @Override
    public UserDeleteResponseDto deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setIsDeleted(true);
        user.setDeletedBy(username);
        user.setDeletedAt(java.time.LocalDateTime.now());

        userRepository.save(user);
        return new UserDeleteResponseDto(user.getId(), username);
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
        PageRequest pageable = PageRequest.of(page, size, searchParam.getSortDirection(), searchParam.getSortBy());

        return userRepository.findByUsernameContainingOrEmailContainingOrPhoneNumberContaining(
                searchParam.getUsername(),
                searchParam.getEmail(),
                searchParam.getPhoneNumber(),
                pageable
        );
    }

    /**
     * 사용자 로그인 처리.
     * 유효한 사용자 정보를 입력받아 JWT 토큰을 생성하고 반환합니다.
     *
     * @param loginRequest 사용자 로그인 요청 데이터
     * @return UserLoginResponseDto 로그인 응답 데이터 (username, token)
     * @throws IllegalArgumentException 유효하지 않은 사용자 정보가 입력된 경우
     */
    @Override
    public UserLoginResponseDto login(UserLoginRequestDto loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtProvider.createToken(user.getUsername(), user.getRole().name());

        return new UserLoginResponseDto(user.getUsername(), token);
    }
}