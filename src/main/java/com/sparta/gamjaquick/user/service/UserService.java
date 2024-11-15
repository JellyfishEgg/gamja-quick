package com.sparta.gamjaquick.user.service;

import com.sparta.gamjaquick.user.dto.request.UserSearchParameter;
import com.sparta.gamjaquick.user.dto.request.UserSignUpRequestDto;
import com.sparta.gamjaquick.user.dto.request.UserUpdateRequestDto;
import com.sparta.gamjaquick.user.dto.response.UserDeleteResponseDto;
import com.sparta.gamjaquick.user.dto.response.UserResponseDto;
import com.sparta.gamjaquick.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    // 회원가입
    UserResponseDto registerUser(UserSignUpRequestDto signUpDto);

    // 특정 사용자 조회
    UserResponseDto getUserById(Long id);

    // 전체 사용자 조회
    List<UserResponseDto> getAllUsers();

    // 사용자 정보 수정
    UserResponseDto updateUser(Long id, UserUpdateRequestDto updateDto);

    // 사용자 삭제
    UserDeleteResponseDto deleteUser(Long id, String deletedBy);

    // 사용자 검색 및 페이징 처리
    Page<User> searchUsers(UserSearchParameter searchParam, int page, int size);
}
