package com.sparta.gamjaquick.orderItem.dto.response;

import com.sparta.gamjaquick.orderItem.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
public class OrderItemResponseDto {
    private UUID orderItemId;     // 주문 항목 ID
    private UUID menuId;          // 메뉴 ID
    private int quantity;         // 수량
    private int price;        // 개별 메뉴 가격
    private int totalPrice;       // 총액 (수량 * 가격)

    public static OrderItemResponseDto from(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .orderItemId(orderItem.getId())
                .menuId(orderItem.getMenu().getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getOrderPrice())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }
}
