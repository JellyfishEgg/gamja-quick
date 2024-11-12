package com.sparta.gamjaquick.user.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserSignUpRequestDto {
    private String username;
    private String nickname;
    private String email;
    private String password;
    private String phoneNumber;
}