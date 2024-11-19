package com.sparta.gamjaquick.order.repository;

import com.sparta.gamjaquick.common.request.SearchParameter;
import com.sparta.gamjaquick.order.entity.Order;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface OrderRepositoryCustom {
    Page<Order> searchOrders(SearchParameter searchParameter, Long userId, UUID storeId);
}
