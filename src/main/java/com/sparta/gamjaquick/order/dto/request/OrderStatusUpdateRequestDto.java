package com.sparta.gamjaquick.order.dto.request;

import com.sparta.gamjaquick.order.entity.OrderStatus;
import lombok.NoArgsConstructor;

import java.util.UUID;


@NoArgsConstructor
public class OrderStatusUpdateRequestDto {
    private UUID orderId;
    private OrderStatus status;

    public OrderStatusUpdateRequestDto(UUID orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }
}
