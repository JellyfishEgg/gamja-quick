package com.sparta.gamjaquick.order.repository;

import com.sparta.gamjaquick.order.entity.DeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, UUID> {
}
