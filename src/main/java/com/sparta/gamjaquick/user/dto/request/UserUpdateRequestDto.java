package com.sparta.gamjaquick.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    private String nickname;
    private String email;
    private String phoneNumber;
    private Boolean isPublic;
}