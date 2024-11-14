package com.sparta.gamjaquick.orderItem.repository;

import com.sparta.gamjaquick.orderItem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
