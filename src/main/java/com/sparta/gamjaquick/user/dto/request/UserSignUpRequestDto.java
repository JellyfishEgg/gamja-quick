package com.sparta.gamjaquick.user.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserSignUpRequestDto {
    @Size(min = 4, max = 10, message = "사용자 이름은 4자 이상 10자 이하이어야 합니다.")
    private String username;
    private String nickname;

    @jakarta.validation.constraints.Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    @Size(min = 8, max = 15, message = "비밀번호는 8자 이상 15자 이하이어야 합니다.")
    private String password;

    private String phoneNumber;
}