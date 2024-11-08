package com.sparta.gamjaquick.orderItem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_order_items")
public class OrderItem {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "order_id", length = 255, nullable = false)
    private String orderId;

    @Column(name = "menu_id", length = 100, nullable = false)
    private String menuId;

    @Column(name = "quantity", nullable = false, columnDefinition = "int default 1")
    private int quantity = 1;

    @Column(name = "order_price", nullable = false)
    private int orderPrice;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;
}
