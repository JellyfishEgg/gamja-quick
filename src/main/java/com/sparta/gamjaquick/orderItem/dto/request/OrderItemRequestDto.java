package com.sparta.gamjaquick.orderItem.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderItemRequestDto {

    private UUID menuId;  // 주문할 메뉴의 ID
    private int quantity;  // 주문할 메뉴의 수량

    public OrderItemRequestDto(UUID menuId, int quantity) {
        this.menuId = menuId;
        this.quantity = quantity;
    }

    // Getter 어노테이션으로 대체
//    public UUID getMenuId() {
//        return menuId;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
}
