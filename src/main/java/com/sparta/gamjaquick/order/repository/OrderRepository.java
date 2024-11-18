package com.sparta.gamjaquick.order.repository;

import com.sparta.gamjaquick.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Page<Order> findAllByUserIdAndIsDeletedFalse(Long userId, Pageable pageable); //고객이 본인의 전체 주문 내역 조회
    Page<Order> findAllByStoreIdAndIsDeletedFalse(UUID storeId, Pageable pageable); //가게주인이 본인 가게에 들어온 전체 주문 내역 조회

    Optional<Order> findByIdAndUserIdAndIsDeletedFalse(UUID orderId, Long userId); //고객 본인의 주문 내역 단건 조회
    Optional<Order> findByIdAndStoreIdAndIsDeletedFalse(UUID orderId, UUID storeId); //가게주인의 가게 주문 내역 단건 조회


}
