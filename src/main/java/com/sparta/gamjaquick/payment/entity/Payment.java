package com.sparta.gamjaquick.payment.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.payment.dto.request.PaymentCreateRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_payments")
public class Payment extends AuditingFields {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "order_id", length = 100, nullable = false)
    private UUID orderId;

    @Column(name = "payment_amount", length = 100, nullable = false)
    private String paymentAmount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING; // 기본값 설정;

    @Column(name = "payment_key", nullable = false)
    private String paymentKey;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Payment(PaymentCreateRequestDto requestDto) {
        this.orderId = requestDto.getOrderId();      // Order ID 설정
        this.paymentAmount = requestDto.getPaymentAmount(); // 결제 금액 설정
        this.paymentKey = requestDto.getPaymentKey(); // Payment Key 설정
        this.status = PaymentStatus.PENDING;         // 초기 상태를 PENDING으로 설정
    }


    public void updateStatus(PaymentStatus status) {
        this.status = status;
    }

    public void changeToDeleted() {
        this.isDeleted = true;
    }


//    @Column(name = "failure_reason")
//    private String failureReason;       // 결제가 실패시 이유(잔액부족, 은행시스템이나 카드사 점검중, 정지된 카드 등등)

}

