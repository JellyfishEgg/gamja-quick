package com.sparta.gamjaquick.payment.repository;

import com.sparta.gamjaquick.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

//    List<Payment> findByUserId(Long userId); //고객이 본인의 내역 조회
//    List<Payment> findByStoreId(UUID storeId); //가게주인이 본인 가게에 들어온 내역 조회
}
