package com.sparta.gamjaquick.user.service;

import com.sparta.gamjaquick.user.dto.request.UserLoginRequestDto;
import com.sparta.gamjaquick.user.dto.request.UserSearchParameter;
import com.sparta.gamjaquick.user.dto.request.UserSignUpRequestDto;
import com.sparta.gamjaquick.user.dto.request.UserUpdateRequestDto;
import com.sparta.gamjaquick.user.dto.response.UserDeleteResponseDto;
import com.sparta.gamjaquick.user.dto.response.UserLoginResponseDto;
import com.sparta.gamjaquick.user.dto.response.UserResponseDto;
import com.sparta.gamjaquick.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    /**
     * 회원가입
     * @param signUpDto 사용자 회원가입 정보를 담은 DTO
     * @return UserResponseDto
     */
    UserResponseDto registerUser(UserSignUpRequestDto signUpDto);

    /**
     * 특정 사용자 조회
     * @param id 사용자 ID
     * @return UserResponseDto
     */
    UserResponseDto getUserById(Long id);

    /**
     * 전체 사용자 조회
     * @return List<UserResponseDto>
     */
    List<UserResponseDto> getAllUsers();

    /**
     * 사용자 정보 수정
     * @param username 인증된 사용자 이름
     * @param updateDto 수정할 사용자 정보가 담긴 DTO
     * @return UserResponseDto
     */
    UserResponseDto updateUser(String username, UserUpdateRequestDto updateDto);

    /**
     * 사용자 삭제
     * @param username 인증된 사용자 이름
     * @return UserDeleteResponseDto
     */
    UserDeleteResponseDto deleteUser(String username);

    /**
     * 사용자 검색 및 페이징 처리
     * @param searchParam 검색 조건을 담은 DTO
     * @param page 페이지 번호
     * @param size 페이지당 항목 수
     * @return Page<User>
     */
    Page<User> searchUsers(UserSearchParameter searchParam, int page, int size);

    /**
     * 사용자 로그인 처리
     * 입력된 로그인 데이터를 기반으로 JWT 토큰을 생성하여 반환
     *
     * @param loginRequest 사용자 로그인 요청 데이터
     * @return UserLoginResponseDto 로그인 응답 데이터 (username, token)
     */
    UserLoginResponseDto login(UserLoginRequestDto loginRequest);
}