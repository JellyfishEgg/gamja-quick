package com.sparta.gamjaquick.infra.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AiApiRequestDto {

    private List<Content> contents;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {
        private List<Part> parts;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Part {
        private String text;
    }

    public static AiApiRequestDto createRequest(String text) {
        Part part = new Part(text);
        Content content = new Content(List.of(part));
        return new AiApiRequestDto(List.of(content));
    }

}
