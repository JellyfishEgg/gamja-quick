package com.sparta.gamjaquick.order.dto.response;

import com.sparta.gamjaquick.order.entity.Order;
import com.sparta.gamjaquick.order.entity.OrderStatus;
import com.sparta.gamjaquick.order.entity.OrderType;
import com.sparta.gamjaquick.orderItem.dto.response.OrderItemResponseDto;
import com.sparta.gamjaquick.payment.dto.response.PaymentResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
public class OrderResponseDto {

    private UUID orderId;                    // 주문 ID
    private String orderNumber;              // 주문 번호
    private Long userId;                     // 사용자 ID
    private UUID storeId;
    private LocalDateTime orderDate = LocalDateTime.now();// 가게 ID
    private OrderStatus orderStatus;         // 주문 상태
    private OrderType orderType;             // 주문 타입 (e.g., online, offline)
    private int totalPrice;                  // 총 가격
    private String cancelReason;             // 주문 취소 사유 (취소된 경우)
    private boolean isDeleted;               // 소프트 삭제 여부

    // AuditingFields
    private LocalDateTime createdAt;         // 주문 생성일시
    private String createdBy;                // 생성자
    private LocalDateTime updatedAt;         // 주문 업데이트일시
    private String updatedBy;                // 수정자
    private LocalDateTime deletedAt;         // 삭제일시
    private String deletedBy;                // 삭제자

    private List<OrderItemResponseDto> orderItems;  // 주문 항목 리스트
    private DeliveryInfoResponseDto deliveryInfo;           // 배달 정보
    private PaymentResponseDto payment;             // 결제 정보

    // Order 엔티티에서 OrderResponseDto 생성
    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUserId())
                .storeId(order.getStoreId())
                .orderStatus(order.getStatus())
                .orderType(order.getType())
                .totalPrice(order.getTotalPrice())
                .cancelReason(order.getCancelReason())
                .isDeleted(order.isDeleted())

                // AuditingFields 필드들
                .createdAt(order.getCreatedAt())
                .createdBy(order.getCreatedBy())
                .updatedAt(order.getUpdatedAt())
                .updatedBy(order.getUpdatedBy())
                .deletedAt(order.getDeletedAt())
                .deletedBy(order.getDeletedBy())

                // 주문 항목 리스트
                .orderItems(order.getOrderItems().stream()
                        .map(OrderItemResponseDto::from)
                        .collect(Collectors.toList()))

                // 배달 및 결제 정보
                .deliveryInfo(DeliveryInfoResponseDto.from(order.getDeliveryInfo()))
                .payment(PaymentResponseDto.from(order.getPayment()))
                .build();
    }
}
