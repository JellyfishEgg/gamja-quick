package com.sparta.gamjaquick.order.dto.request;

import com.sparta.gamjaquick.orderItem.dto.request.OrderItemRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    private UUID storeId;
    private String orderNumber;
    private List<OrderItemRequestDto> orderItems;
    private String orderType;  // orderType 필드 정의
    private PaymentInfo payment;


    public void setStoreId(UUID storeId) {
        this.storeId = storeId;
    }

    public void setOrderItems(List<OrderItemRequestDto> orderItems) {
        this.orderItems = orderItems;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

//    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
//        this.deliveryInfo = deliveryInfo;
//    }

    public void setPayment(PaymentInfo payment) {
        this.payment = payment;
    }

    @Getter
    @Setter
    public static class OrderItemRequestDto {
        private UUID menuId;
        private int quantity;
    }

    @Getter
    @Setter
    public static class DeliveryInfoRequestDto {
        private String address;
        private String request;
    }

    @Getter
    @Setter
    public static class PaymentInfo {
        private int amount;
        private String paymentMethod;
    }
}
