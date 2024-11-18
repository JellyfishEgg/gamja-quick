package com.sparta.gamjaquick.infra.ai.service;

import com.sparta.gamjaquick.infra.ai.dto.AiApiResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiApiServiceTest {

    @Autowired
    private AiApiService aiApiService;

    @Test
    void test() {
        // given
        String prompt = "감자 메뉴 설명해줘.";

        // when
        AiApiResponseDto responseDto = aiApiService.generateText(prompt);

        // then
        Assertions.assertNotNull(responseDto.getFirstCandidateText());
    }
}