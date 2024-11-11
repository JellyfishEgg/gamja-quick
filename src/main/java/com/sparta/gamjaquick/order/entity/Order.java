package com.sparta.gamjaquick.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_orders")
public class Order {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "user_id", length = 100, nullable = false)
    private Long userId;

    @Column(name = "store_id", length = 100, nullable = false)
    private String storeId;

    @Column(name = "order_number", length = 100, nullable = false)
    private String orderNumber;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "total_price", length = 225, nullable = false)
    private String totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private orderStatus status;

    @Column(name = "cancel_reason", length = 100, nullable = false)
    private String cancelReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private orderType type;

}

enum orderStatus {
    PENDING,
    PREPARING,
    IN_PROGRESS,
    SHIPPING,
    COMPLETED,
    CANCELLED
}

enum orderType {
    OFFLINE,
    ONLINE
}
