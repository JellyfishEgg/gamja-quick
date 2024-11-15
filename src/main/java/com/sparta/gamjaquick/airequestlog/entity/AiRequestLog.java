package com.sparta.gamjaquick.airequestlog.entity;

import com.sparta.gamjaquick.infra.ai.dto.AiApiResponseDto;
import com.sparta.gamjaquick.store.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_ai_request_log")
@Entity
public class AiRequestLog {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Comment("요청 로그 고유 ID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @Comment("가게 ID")
    private Store store;

    @Column(nullable = false, length = 512)
    @Comment("요청 내용")
    private String requestContent;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Comment("응답 내용")
    private String responseContent;

    @Column(nullable = false)
    @Comment("요청 처리 시간")
    private Long processingTime; // 처리 시간 (밀리초)

    @Column(nullable = false)
    @Comment("AI 모델 버전")
    private String modelVersion;

    @Comment("요청 일시")
    private LocalDateTime requestDate;

    @Builder
    private AiRequestLog(Store store, String requestContent, String responseContent, Long processingTime, String modelVersion, LocalDateTime requestDate) {
        this.store = store;
        this.requestContent = requestContent;
        this.responseContent = responseContent;
        this.processingTime = processingTime;
        this.modelVersion = modelVersion;
        this.requestDate = requestDate;
    }

    /**
     * AI 요청 로그 생성 정적 팩토리 메서드
     * @return AiRequestLog
     */
    public static AiRequestLog from(Store store, String requestContent, AiApiResponseDto responseDto) {
        return AiRequestLog.builder()
                .store(store)
                .requestContent(requestContent)
                .responseContent(responseDto.getFirstCandidateText())
                .processingTime(responseDto.getProcessingTime())
                .modelVersion(responseDto.getModelVersion())
                .requestDate(LocalDateTime.now())
                .build();
    }

}
