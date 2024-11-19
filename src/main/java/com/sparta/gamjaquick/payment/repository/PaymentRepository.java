package com.sparta.gamjaquick.payment.repository;

import com.sparta.gamjaquick.order.entity.Order;
import com.sparta.gamjaquick.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    Optional<Payment> findById(UUID paymentId); //관리자가 내역 조회
//    List<Payment> findByStoreId(UUID storeId); //가게주인이 본인 가게에 들어온 내역 조회..는 알아서 조회하시라..
    Payment findByOrder(Order order);
}
