package com.sparta.gamjaquick.order.dto.request;

import com.sparta.gamjaquick.order.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderStatusUpdateRequestDto {
    private UUID orderId;
    private OrderStatus status;
    private String cancelReason;   // 주문 취소 사유 (취소할 때만 필요)

}
