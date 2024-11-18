package com.sparta.gamjaquick.health.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HealthCheckResponseDto {

    private String health;
    private List<String> activeProfiles;
}
