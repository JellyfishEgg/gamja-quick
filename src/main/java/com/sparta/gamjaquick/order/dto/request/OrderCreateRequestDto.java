package com.sparta.gamjaquick.order.dto.request;

import com.sparta.gamjaquick.order.entity.DeliveryInfo;
import com.sparta.gamjaquick.order.entity.OrderType;
import com.sparta.gamjaquick.orderItem.dto.request.OrderItemRequestDto;
import com.sparta.gamjaquick.payment.dto.request.PaymentCreateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    private Long userId;
    private UUID storeId;
    private String orderNumber;
    private List<OrderItemRequestDto> orderItems;
    private OrderType type;
    private PaymentCreateRequestDto payment;
    private DeliveryInfoRequestDto deliveryInfo;

    public OrderCreateRequestDto(Long userId, UUID storeId, String orderNumber, List<OrderItemRequestDto> orderItems, OrderType type, PaymentCreateRequestDto payment, DeliveryInfoRequestDto deliveryInfo) {
        this.userId = userId;
        this.storeId = storeId;
        this.orderNumber = orderNumber;
        this.orderItems = orderItems;
        this.type = type;
        this.payment = payment;
        this.deliveryInfo = deliveryInfo;
    }


    @Getter
    @Setter
    public static class OrderItemRequestDto {
        private UUID menuId;
        private int quantity;
        private int price;
    }

    @Getter
    @Setter
    public static class DeliveryInfoRequestDto {
        private String address;
        private String request;
    }
}
