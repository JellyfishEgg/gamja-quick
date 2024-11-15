package com.sparta.gamjaquick.infra.ai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "google.ai")
public class ApiConfig {

    private String apiKey;
    private String apiUrl;
    private String instruction;

}
