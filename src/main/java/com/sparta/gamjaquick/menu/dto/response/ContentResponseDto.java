package com.sparta.gamjaquick.menu.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ContentResponseDto {

    private String content;

    public static ContentResponseDto from(String content) {
        return ContentResponseDto.builder().content(content).build();
    }

}
