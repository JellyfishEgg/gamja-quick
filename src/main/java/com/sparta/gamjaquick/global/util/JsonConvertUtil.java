package com.sparta.gamjaquick.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonConvertUtil {

    private final ObjectMapper objectMapper;

    public <T> String convertJavaToJson(T item) {
        try {
            return objectMapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert Java object to JSON", e);
            throw new BusinessException(ErrorCode.JSON_CONVERSION_ERROR);
        }
    }

    public <T> T convertJsonToJava(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            log.error("Failed to convert JSON to Java OBJECT", e);
            throw new BusinessException(ErrorCode.JSON_CONVERSION_ERROR);
        }
    }

}
