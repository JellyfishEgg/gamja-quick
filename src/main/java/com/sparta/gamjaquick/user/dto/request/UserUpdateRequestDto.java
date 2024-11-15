package com.sparta.gamjaquick.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    private String nickname;

    @jakarta.validation.constraints.Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    private String phoneNumber;
    private Boolean isPublic;
}