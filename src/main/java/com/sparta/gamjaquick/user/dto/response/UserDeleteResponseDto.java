package com.sparta.gamjaquick.user.dto.response;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UserDeleteResponseDto {
    private String message;
    private Long userId;
    private LocalDateTime deletedAt;
    private String deletedBy;

    public UserDeleteResponseDto(Long userId, String deletedBy) {
        this.message = "유저를 성공적으로 삭제하였습니다.";
        this.userId = userId;
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = deletedBy;
    }
}