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

    //썩은 감자가 스웨거 커스텀때문에 추가한 코드긴 한데여... 저도 잘 이해가 안대서 .... 도저히 path를 어떻게 넣는건지...
    public static ErrorResponseDto from(ErrorCode errorCode) {
        return ErrorResponseDto.builder()
                .status("failure") // 기본 상태를 "failure"로 설정
                .message(errorCode.name()) // ErrorCode의 메시지를 사용
                .error(ErrorDetails.of(errorCode, errorCode.getMessage(), "N/A")) // 기본 에러 세부사항 설정
                .timestamp(LocalDateTime.now()) // 현재 시간 설정
                .build();
    }

}
