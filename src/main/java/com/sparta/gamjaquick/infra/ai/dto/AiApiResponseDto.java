package com.sparta.gamjaquick.infra.ai.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AiApiResponseDto {

    private List<Candidate> candidates;
    private String modelVersion;

    public String getFirstCandidateText() {
        return candidates.stream()
                .findFirst()
                .map(Candidate::getContent)
                .map(Content::getParts)
                .flatMap(parts -> parts.stream().findFirst())
                .map(Part::getText)
                .orElse("");
    }

    @Getter
    public static class Candidate {

        private Content content;
        private String finishReason;
    }

    @Getter
    public static class Content {

        private List<Part> parts;
        private String role;
    }

    @Getter
    public static class Part {

        private String text;
    }

}
