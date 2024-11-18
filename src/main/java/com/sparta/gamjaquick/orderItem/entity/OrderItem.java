package com.sparta.gamjaquick.orderItem.entity;

import com.sparta.gamjaquick.menu.entity.Menu;
import com.sparta.gamjaquick.order.entity.Order;
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
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)  // 지연 로딩(Lazy Loading)
    @JoinColumn(name = "menu_id", nullable = false)  // 외래 키 설정
    private Menu menu;

    @Column(name = "quantity", nullable = false, columnDefinition = "int default 1")
    private int quantity = 1;

    @Column(name = "order_price", nullable = false)
    private int orderPrice;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    public OrderItem(Menu menu, int quantity, int orderPrice) {
        this.menu = menu;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
    }
}
