package com.sparta.gamjaquick.order.dto.request;

import com.sparta.gamjaquick.order.entity.DeliveryInfo;
import com.sparta.gamjaquick.order.entity.OrderType;
import com.sparta.gamjaquick.orderItem.dto.request.OrderItemRequestDto;
import com.sparta.gamjaquick.payment.dto.request.PaymentCreateRequestDto;
import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    private User user;
    private Store store;
    private String orderNumber;
    private List<OrderItemRequestDto> orderItems;
    private OrderType type;
    private PaymentCreateRequestDto payment;
    private DeliveryInfoRequestDto deliveryInfo;

    public OrderCreateRequestDto(User user, Store store, String orderNumber, List<OrderItemRequestDto> orderItems, OrderType type, PaymentCreateRequestDto payment, DeliveryInfoRequestDto deliveryInfo) {
        this.user = user;
        this.store = store;
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
