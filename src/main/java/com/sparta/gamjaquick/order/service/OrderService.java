package com.sparta.gamjaquick.order.service;


import com.sparta.gamjaquick.common.response.PageResponseDto;
import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.global.error.exception.BusinessException;
import com.sparta.gamjaquick.menu.entity.Menu;
import com.sparta.gamjaquick.menu.repository.MenuRepository;
import com.sparta.gamjaquick.order.dto.request.OrderCreateRequestDto;
import com.sparta.gamjaquick.order.dto.request.OrderStatusUpdateRequestDto;
import com.sparta.gamjaquick.order.dto.response.OrderResponseDto;
import com.sparta.gamjaquick.order.entity.DeliveryInfo;
import com.sparta.gamjaquick.order.entity.Order;
import com.sparta.gamjaquick.order.entity.OrderStatus;
import com.sparta.gamjaquick.order.repository.DeliveryInfoRepository;
import com.sparta.gamjaquick.order.repository.OrderRepository;
import com.sparta.gamjaquick.orderItem.entity.OrderItem;
import com.sparta.gamjaquick.orderItem.repository.OrderItemRepository;
import com.sparta.gamjaquick.payment.entity.Payment;
import com.sparta.gamjaquick.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final OrderItemRepository orderItemRepository;
    private final MenuRepository menuRepository;

    public OrderResponseDto createOrder(OrderCreateRequestDto requestDto) {
        // 배송지정보저장
        DeliveryInfo deliveryInfo = new DeliveryInfo(
                requestDto.getDeliveryInfo().getAddress(),
                requestDto.getDeliveryInfo().getRequest()
        );
        deliveryInfoRepository.save(deliveryInfo);

        // 결제정보저장
        Payment payment = new Payment(requestDto.getPayment());
        paymentRepository.save(payment);

        // 총금액계산, 주문메뉴저장
        List<OrderItem> orderItems = new ArrayList<>();
        int totalPrice = 0;
        for (OrderCreateRequestDto.OrderItemRequestDto itemDto : requestDto.getOrderItems()) {
            Menu menu = menuRepository.findById(itemDto.getMenuId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.MENU_NOT_FOUND));
            totalPrice += itemDto.getPrice() * itemDto.getQuantity();

            OrderItem orderItem = new OrderItem(menu, itemDto.getQuantity(), itemDto.getPrice());
            orderItems.add(orderItem);
        }

        // 완성된주문서저장
        Order order = new Order(
                requestDto.getUserId(),
                requestDto.getStoreId(),
                requestDto.getOrderNumber(),
                totalPrice,  // 계산된 총 금액
                requestDto.getType(),
                deliveryInfo,
                payment,
                orderItems
        );

        // 5. 주문 항목 저장 (배달 정보 및 결제 정보와 함께)
        orderItemRepository.saveAll(orderItems);

        // 6. 주문 저장
        order = orderRepository.save(order);

        // 7. 주문 응답 DTO 반환
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

