package com.sparta.gamjaquick.infra.ai.service;

import com.sparta.gamjaquick.infra.ai.client.AiApiClient;
import com.sparta.gamjaquick.infra.ai.dto.AiApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiApiService {

    private final AiApiClient aiApiClient;

    /**
     * 주어진 프롬프트를 기반으로 AI 모델을 사용하여 텍스트를 생성합니다.
     *
     * @param prompt AI 모델에 제공할 입력 텍스트 (프롬프트)
     * @return AI 모델이 생성한 응답 텍스트. 응답이 없거나 오류 발생 시 빈 문자열 반환
     */
    public String generateText(String prompt) {
        AiApiResponseDto response = aiApiClient.sendRequest(prompt);

        // TODO: Log 테이블에 값 저장
        return response.getFirstCandidateText();
    }

}
