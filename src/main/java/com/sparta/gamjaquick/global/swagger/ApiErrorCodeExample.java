package com.sparta.gamjaquick.global.swagger;

import com.sparta.gamjaquick.global.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorCodeExample {
    ErrorCode value();
}
