package com.sparta.gamjaquick.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자 로그인 응답 DTO
 * 사용자 이름(username)과 JWT 토큰(token)을 포함
 */
@Getter
@AllArgsConstructor
public class UserLoginResponseDto {
    private String username;
    private String token;
}