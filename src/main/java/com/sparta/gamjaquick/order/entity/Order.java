package com.sparta.gamjaquick.order.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.order.dto.request.OrderCreateRequestDto;
import com.sparta.gamjaquick.orderItem.entity.OrderItem;
import com.sparta.gamjaquick.payment.dto.request.PaymentCreateRequestDto;
import com.sparta.gamjaquick.payment.entity.Payment;
import com.sparta.gamjaquick.payment.entity.PaymentStatus;
import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.user.entity.User;
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
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)  // 여러 주문이 하나의 유저에 속함
    @JoinColumn(name = "user_id")  // 외래 키 이름
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)  // 여러 주문이 하나의 유저에 속함
    @JoinColumn(name = "store_id")  // 외래 키 이름
    private Store store;

    @Column(name = "order_number", length = 100, nullable = false)
    private String orderNumber;

    @Setter
    @Builder.Default
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Setter
    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Builder.Default
    @OneToOne(cascade = CascadeType.ALL) // 배달 정보와 1:1 관계
    @JoinColumn(name = "delivery_info_id", nullable = false)
    private DeliveryInfo deliveryInfo = new DeliveryInfo();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Setter
    @OneToOne(mappedBy = "order", orphanRemoval = true)
    private Payment payment;

    // 레파지토리에서 아이디 찾아서 하기


    @Setter
    @Column(name = "cancel_reason", length = 100, nullable = false)
    private String cancelReason = "";

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OrderType type;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

public Order(User user, Store store, int totalPrice, OrderType type, DeliveryInfo deliveryInfo, List<OrderItem> orderItems) {
    this.user = user;
    this.store = store;
    //this.orderNumber = orderNumber;
    this.totalPrice = totalPrice;
    this.type = type;
    this.deliveryInfo = deliveryInfo; // 배달 정보 설정
//    this.payment = payment;           // 결제 정보 설정
    this.orderItems = orderItems;     // 주문 항목 설정
    this.status = OrderStatus.PENDING; // 기본 상태 PENDING으로 설정
}

    // 주문 번호 생성 로직 추가
    @PrePersist
    public void generateOrderNumber() {
        this.orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
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


