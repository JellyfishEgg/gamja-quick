package com.sparta.gamjaquick.order.service;

import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.common.response.PageResponseDto;
import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.global.error.exception.BusinessException;
import com.sparta.gamjaquick.order.dto.request.OrderCreateRequestDto;
import com.sparta.gamjaquick.order.dto.request.OrderStatusUpdateRequestDto;
import com.sparta.gamjaquick.order.dto.response.OrderResponseDto;
import com.sparta.gamjaquick.order.entity.DeliveryInfo;
import com.sparta.gamjaquick.order.entity.Order;
import com.sparta.gamjaquick.order.entity.OrderStatus;
import com.sparta.gamjaquick.order.repository.DeliveryInfoRepository;
import com.sparta.gamjaquick.order.repository.OrderRepository;
import com.sparta.gamjaquick.payment.entity.Payment;
import com.sparta.gamjaquick.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryInfoRepository deliveryInfoRepository;
    private final PaymentRepository paymentRepository;

    public OrderResponseDto createOrder(OrderCreateRequestDto requestDto) {
        // 배송지정보 저장
        DeliveryInfo deliveryInfo = new DeliveryInfo(
                requestDto.getDeliveryInfo().getAddress(),
                requestDto.getDeliveryInfo().getRequest()
        );
        deliveryInfoRepository.save(deliveryInfo);

        // 결제할 방법 저장
        Payment payment = new Payment(
                requestDto.getPayment().getPaymentAmount(),
                requestDto.getPayment().getPaymentMethod()
        );
        paymentRepository.save(payment);

        Order order = new Order(
                requestDto.getUserId(),
                requestDto.getStoreId(),
                requestDto.getOrderNumber(),
                requestDto.getTotalPrice(),
                requestDto.getType(),
                deliveryInfo // 배달 정보 설정
        );
        // 4. Order 객체 저장
        order = orderRepository.save(order);

        // 5. OrderResponseDto 반환
        return OrderResponseDto.from(order);
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        return OrderResponseDto.from(order);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<OrderResponseDto> getAllOrders(Long userId, UUID storeId, Pageable pageable) {
        Page<Order> orders;
        if (userId != null) {
            orders = orderRepository.findAllByUserIdAndIsDeletedFalse(userId, pageable);
        } else if (storeId != null) {
            orders = orderRepository.findAllByStoreIdAndIsDeletedFalse(storeId, pageable);
        } else {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        return PageResponseDto.of(orders.map(OrderResponseDto::from));
    }

    public OrderResponseDto updateOrderStatus(UUID orderId, OrderStatusUpdateRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        if (requestDto.getStatus() == OrderStatus.CANCELLED) {
            order.cancelOrder(requestDto.getCancelReason());
        } else {
            order.updateStatus(requestDto.getStatus());
        }

        return OrderResponseDto.from(order);
    }

    //소프트 삭제
    public void deleteOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        order.changeToDeleted();
    }
}

