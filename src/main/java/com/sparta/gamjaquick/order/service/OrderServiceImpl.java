package com.sparta.gamjaquick.order.service;

import com.sparta.gamjaquick.order.dto.request.OrderCreateRequestDto;
import com.sparta.gamjaquick.order.dto.response.OrderResponseDto;
import com.sparta.gamjaquick.order.entity.Order;
import com.sparta.gamjaquick.order.repository.OrderRepository;
import com.sparta.gamjaquick.user.entity.User;

import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponseDto createOrder(OrderCreateRequestDto orderRequestDto) {
        return null;  // DTO 반환
    }

    @Override
    public Order updateOrderStatus(UUID orderId, String orderStatus) {
        return null;
    }

    @Override
    public Order getOrderById(UUID id) {
        return null;
    }

    @Override
    public List<Order> getAllOrders(User user) {
        return List.of();
    }

    @Override
    public void cancelOrder(UUID orderId, String cancelReason) {

    }
}
