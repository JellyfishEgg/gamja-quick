package com.sparta.gamjaquick.health.controller;

import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.health.dto.HealthCheckResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HealthCheckController {

    private final Environment environment;

    @GetMapping("/health")
    public ApiResponseDto<?> healthCheck() {
        HealthCheckResponseDto result = HealthCheckResponseDto.builder()
                .health("ok")
                .activeProfiles(List.of(environment.getActiveProfiles()))
                .build();
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }
}
