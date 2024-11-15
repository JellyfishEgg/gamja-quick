package com.sparta.gamjaquick.airequestlog.service;

import com.sparta.gamjaquick.airequestlog.entity.AiRequestLog;
import com.sparta.gamjaquick.airequestlog.repository.AiRequestLogRepository;
import com.sparta.gamjaquick.infra.ai.dto.AiApiResponseDto;
import com.sparta.gamjaquick.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AiRequestLogService {

    private final AiRequestLogRepository aiRequestLogRepository;

    // 로그 저장
    public void createLog(Store store, String requestContent, AiApiResponseDto responseDto) {
        AiRequestLog createAiRequestLog = AiRequestLog.from(store, requestContent, responseDto);
        aiRequestLogRepository.save(createAiRequestLog);
    }

}
