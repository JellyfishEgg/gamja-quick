    package com.sparta.gamjaquick.config.security.jwt;

    import lombok.Getter;
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.context.annotation.Configuration;


    @Getter
    @Configuration
    @ConfigurationProperties(prefix = "jwt")
    public class JwtProperties {

        private String secretKey;
        private long expiration;
    }