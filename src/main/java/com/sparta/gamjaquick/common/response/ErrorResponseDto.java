package com.sparta.gamjaquick.common.response;

import com.sparta.gamjaquick.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class ErrorResponseDto {

    private String status;
    private String message;
    private ErrorDetails error;
    private LocalDateTime timestamp;

    public static ErrorResponseDto ERROR(String message, ErrorDetails error) {
        return ErrorResponseDto.builder()
                .status("failure")
                .message(message)
                .error(error)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Builder
    @Getter
    public static class ErrorDetails {

        private String code;
        private String details;
        private String path;

        public static ErrorDetails of(ErrorCode errorCode, String details, String path) {
            return ErrorDetails.builder()
                    .code(errorCode.getCode())
                    .details(details)
                    .path(path)
                    .build();
        }

        public static ErrorDetails of(ErrorCode errorCode, BindingResult bindingResult, String path) {
            return ErrorDetails.builder()
                    .code(errorCode.getCode())
                    .details(bindingResult.getFieldErrors().getFirst().getDefaultMessage())
                    .path(path)
                    .build();
        }
    }

}
