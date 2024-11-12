package com.sparta.gamjaquick.order.service;

import com.sparta.gamjaquick.order.dto.request.OrderCreateRequestDto;
import com.sparta.gamjaquick.order.dto.response.OrderResponseDto;
import com.sparta.gamjaquick.order.entity.Order;
import com.sparta.gamjaquick.user.entity.User;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponseDto createOrder(OrderCreateRequestDto orderRequestDto);
    Order updateOrderStatus(UUID orderId, String orderStatus);
    Order getOrderById(UUID orderId);
    List<Order> getAllOrders(User user);
    void cancelOrder(UUID orderId, String cancelReason);

}
