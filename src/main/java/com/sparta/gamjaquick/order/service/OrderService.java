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
import com.sparta.gamjaquick.payment.entity.PaymentStatus;
import com.sparta.gamjaquick.payment.repository.PaymentRepository;
import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.store.service.StoreService;
import com.sparta.gamjaquick.user.entity.User;
import com.sparta.gamjaquick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final StoreService storeService;
    private final UserRepository userRepository;

    public OrderResponseDto createOrder(OrderCreateRequestDto requestDto) {
        // 배송 정보 저장
        DeliveryInfo deliveryInfo = new DeliveryInfo(
                requestDto.getDeliveryInfo().getAddress(),
                requestDto.getDeliveryInfo().getRequest()
        );
        deliveryInfoRepository.save(deliveryInfo);

        // 사용자 및 가게 정보 조회
        User findUser = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Store findStore = storeService.findById(requestDto.getStoreId().toString());

        // 3. 주문 생성 (orderItems는 이후 추가)
        Order order = new Order(
                findUser,
                findStore,
                0, // totalPrice는 이후 계산
                requestDto.getType(),
                deliveryInfo,
                new ArrayList<>() // 비어 있는 리스트 초기화
        );
        order.setCancelReason(""); // 기본값 설정
        order.setOrderDate(LocalDateTime.now()); // 주문 날짜 설정

        // 4. 먼저 Order를 저장 (ID를 생성하기 위함)
        order = orderRepository.save(order);

        // 5. 주문 항목 및 총 가격 계산
        List<OrderItem> orderItems = new ArrayList<>();
        int totalPrice = 0;
        for (OrderCreateRequestDto.OrderItemRequestDto itemDto : requestDto.getOrderItems()) {
            Menu menu = menuRepository.findById(itemDto.getMenuId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.MENU_NOT_FOUND));
            totalPrice += itemDto.getPrice() * itemDto.getQuantity();

            OrderItem orderItem = new OrderItem(menu, itemDto.getQuantity(), itemDto.getPrice());
            orderItem.setOrder(order); // Order 설정
            orderItems.add(orderItem);
        }

        // 6. OrderItem 저장
        orderItemRepository.saveAll(orderItems);

        // 7. 총 가격 설정 후 Order 업데이트
        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);
        order = orderRepository.save(order);

        // 결제 정보 생성 및 저장
        Payment payment = new Payment(requestDto.getPayment(), totalPrice);
        payment.setOrder(order); // 저장된 Order 설정
        paymentRepository.save(payment);


        // Order에 Payment 설정
        order.setPayment(payment);
        orderRepository.save(order);

        // 반환
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
        // 주문 존재 확인
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        // 주문 성공
        if (requestDto.getStatus() == OrderStatus.COMPLETED) {
            Payment payment = order.getPayment(); // 결제 성공으로 업데이트
            payment.updateStatus(PaymentStatus.SUCCESS);
            payment.setAdditionalPaymentInfo(payment.getId(), "payment_key_generated", LocalDateTime.now());
            paymentRepository.save(payment);

            order.updateStatus(requestDto.getStatus());

        // 주문 취소
        } else if (requestDto.getStatus() == OrderStatus.CANCELLED) {
            Payment payment = order.getPayment();
            payment.updateStatus(PaymentStatus.CANCELLED);
            payment.setRefund(payment.getId(), payment.getPaymentMethod(), payment.getPaymentAmount(), LocalDateTime.now());
            paymentRepository.save(payment);

            order.updateStatus(OrderStatus.CANCELLED);

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

