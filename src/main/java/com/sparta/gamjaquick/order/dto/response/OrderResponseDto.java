package com.sparta.gamjaquick.order.dto.response;

import com.sparta.gamjaquick.order.entity.Order;
import com.sparta.gamjaquick.order.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderResponseDto {
    private UUID orderId;
    private String orderNumber;
    private LocalDateTime orderDate;
    private String totalPrice;
    private OrderStatus status;
    private String cancelReason;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.orderNumber = order.getOrderNumber();
        this.orderDate = order.getOrderDate();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
        this.cancelReason = order.getCancelReason();
    }

    public UUID getId() {
        return orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getCancelReason() {
        return cancelReason;
    }

}
