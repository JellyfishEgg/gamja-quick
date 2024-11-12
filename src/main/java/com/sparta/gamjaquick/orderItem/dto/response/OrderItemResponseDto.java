package com.sparta.gamjaquick.orderItem.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderItemResponseDto {
    private UUID orderItemId;     // 주문 항목 ID
    private UUID menuId;          // 메뉴 ID
    private int quantity;         // 수량
    private int unitPrice;        // 개별 메뉴 가격
    private int totalPrice;       // 총액 (수량 * 가격)

    public OrderItemResponseDto(UUID orderItemId, UUID menuId, int quantity, int unitPrice, int totalPrice) {
        this.orderItemId = orderItemId;
        this.menuId = menuId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }
}
