package com.sparta.gamjaquick.global.swagger;

import io.swagger.v3.oas.models.examples.Example;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExampleHolder {
    private final int code; // HTTP 상태 코드
    private final String name; // 예제 이름
    private final Example holder; // Swagger Example 객체
}

