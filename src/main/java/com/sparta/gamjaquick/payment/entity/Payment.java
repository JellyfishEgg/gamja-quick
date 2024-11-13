package com.sparta.gamjaquick.payment.entity;

import com.sparta.gamjaquick.common.AuditingFields;
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
@Table(name = "p_payments")
public class Payment extends AuditingFields {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "order_id", length = 100, nullable = false)
    private String orderId;

    @Column(name = "payment_amount", length = 100, nullable = false)
    private String paymentAmount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "payment_key", nullable = false)
    private String paymentKey;

//    @Column(name = "failure_reason")
//    private String failureReason;       // 결제가 실패시 이유(잔액부족, 은행시스템이나 카드사 점검중, 정지된 카드 등등)

}

