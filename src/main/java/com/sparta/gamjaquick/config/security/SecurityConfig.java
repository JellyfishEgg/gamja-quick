package com.sparta.gamjaquick.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Spring Security의 주요 설정을 정의하는 빈 등록 : SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화
                .csrf(csrf -> csrf.disable())
                // 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 회원가입 & 로그인은 인증 없이 허용
                        .requestMatchers("/api/users/signup", "/api/users/login",
                                "/swagger-ui/**","/swagger-resources/**",
                                "/v3/api-docs/**").permitAll()
                        // 나머지 요청은 인증 필요
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    /**
     * 비밀번호를 암호화하기 위한 빈 등록 : BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}