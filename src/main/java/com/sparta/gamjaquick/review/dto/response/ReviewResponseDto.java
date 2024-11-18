package com.sparta.gamjaquick.review.dto.response;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ReviewResponseDto {
    private UUID reviewId;
    private String storeId;
    private String orderId;
    private String nickname;
    private int rating;
    private String content;
    private Boolean isHidden;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;

    public ReviewResponseDto(UUID reviewId, String storeId, String orderId, String nickname, int rating, String content, Boolean isHidden, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isDeleted) {
        this.reviewId = reviewId;
        this.storeId = storeId;
        this.orderId = orderId;
        this.nickname = nickname;
        this.rating = rating;
        this.content = content;
        this.isHidden = isHidden;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }
}