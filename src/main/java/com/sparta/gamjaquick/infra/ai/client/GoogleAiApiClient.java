package com.sparta.gamjaquick.infra.ai.client;

import com.sparta.gamjaquick.global.util.JsonConvertUtil;
import com.sparta.gamjaquick.infra.ai.config.ApiConfig;
import com.sparta.gamjaquick.infra.ai.dto.AiApiRequestDto;
import com.sparta.gamjaquick.infra.ai.dto.AiApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j(topic = "GOOGLE AI API")
@Component
@RequiredArgsConstructor
public class GoogleAiApiClient implements AiApiClient {

    private final RestTemplate restTemplate;
    private final JsonConvertUtil jsonConvertUtil;
    private final ApiConfig apiConfig;

    public AiApiResponseDto sendRequest(String prompt) {
        AiApiRequestDto aiApiRequestDto = AiApiRequestDto.createRequest(prompt + apiConfig.getInstruction());
        return sendRequest(aiApiRequestDto);
    }

    // GOOGLE AI API에 요청을 보내고 응답을 받아 처리
    private AiApiResponseDto sendRequest(AiApiRequestDto aiApiRequestDto) {

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        String jsonRequest = jsonConvertUtil.convertJavaToJson(aiApiRequestDto);
        final HttpEntity<?> requestEntity = new HttpEntity<>(jsonRequest, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(getApiRequestUri(), HttpMethod.POST, requestEntity, String.class);
        System.out.println("responseEntity = " + responseEntity);
        log.info("GOOGLE AI API Status Code : {}", responseEntity.getStatusCode());

        return jsonConvertUtil.convertJsonToJava(responseEntity.getBody(), AiApiResponseDto.class);
    }

    // AI API 요청에 필요한 URI를 생성
    private URI getApiRequestUri() {
        URI uri = UriComponentsBuilder
                .fromUriString(apiConfig.getApiUrl())
                .queryParam("key", apiConfig.getApiKey())
                .encode()
                .build()
                .toUri();

        log.info("GOOGLE AI API URI: {}", uri);
        return uri;
    }

}
