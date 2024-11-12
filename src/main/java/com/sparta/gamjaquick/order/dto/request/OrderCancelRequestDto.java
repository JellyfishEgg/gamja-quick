package com.sparta.gamjaquick.order.dto.request;

import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class OrderCancelRequestDto {
    private UUID orderId;
    private String cancelReason;

    public OrderCancelRequestDto(UUID orderId, String cancelReason) {
        this.orderId = orderId;
        this.cancelReason = cancelReason;
    }
}
