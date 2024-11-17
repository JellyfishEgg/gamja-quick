package com.sparta.gamjaquick.order.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.order.dto.request.OrderCreateRequestDto;
import com.sparta.gamjaquick.orderItem.entity.OrderItem;
import com.sparta.gamjaquick.payment.dto.request.PaymentCreateRequestDto;
import com.sparta.gamjaquick.payment.entity.Payment;
import com.sparta.gamjaquick.payment.entity.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_orders")
public class Order extends AuditingFields {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "user_id", length = 100, nullable = false)
    private Long userId;

    @Column(name = "store_id", length = 100, nullable = false)
    private UUID storeId;

    @Column(name = "order_number", length = 100, nullable = false)
    private String orderNumber;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @OneToOne(cascade = CascadeType.ALL) // 배달 정보와 1:1 관계
    @JoinColumn(name = "delivery_info_id", nullable = false)
    private DeliveryInfo deliveryInfo = new DeliveryInfo();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", nullable = true)
    private Payment payment;

    @Column(name = "cancel_reason", length = 100, nullable = false)
    private String cancelReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OrderType type;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

public Order(Long userId, UUID storeId, String orderNumber, int totalPrice, OrderType type, DeliveryInfo deliveryInfo, Payment payment, List<OrderItem> orderItems) {
    this.userId = userId;
    this.storeId = storeId;
    this.orderNumber = orderNumber;
    this.totalPrice = totalPrice;
    this.type = type;
    this.deliveryInfo = deliveryInfo; // 배달 정보 설정
    this.payment = payment;           // 결제 정보 설정
    this.orderItems = orderItems;     // 주문 항목 설정
    this.status = OrderStatus.PENDING; // 기본 상태 PENDING으로 설정
}

    // 소프트 삭제
    public void changeToDeleted() {
        this.isDeleted = true;
    }

    // 주문 취소 처리
    public void cancelOrder(String cancelReason) {
        this.status = OrderStatus.CANCELLED;
        this.cancelReason = cancelReason;
    }

    // 주문 상태 업데이트
    public void updateStatus(OrderStatus status) {
        this.status = status;
    }



}


