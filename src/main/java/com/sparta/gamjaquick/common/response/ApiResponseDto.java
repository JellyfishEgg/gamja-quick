package com.sparta.gamjaquick.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class ApiResponseDto<T> {

    private String status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponseDto<T> success(MessageType messageType, T data) {
        return ApiResponseDto.<T>builder()
                .status("success")
                .message(messageType.getMessage())
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
