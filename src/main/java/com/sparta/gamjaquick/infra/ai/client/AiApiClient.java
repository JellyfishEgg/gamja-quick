package com.sparta.gamjaquick.infra.ai.client;

import com.sparta.gamjaquick.infra.ai.dto.AiApiResponseDto;

public interface AiApiClient {

    AiApiResponseDto sendRequest(String prompt);

}
