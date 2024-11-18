package com.sparta.gamjaquick.payment.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.order.entity.Order;
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
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Setter
    @Column(name = "payment_amount", length = 100, nullable = false)
    private int paymentAmount;

    @Column(name = "payment_method", length = 100, nullable = false)
    private String paymentMethod;

    @Builder.Default
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Builder.Default
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING; // 기본값 설정;

    @Column(name = "payment_key", nullable = false)
    private String paymentKey;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "refund_method", nullable = false)
    private String refundMethod;

    @Column(name = "refund_amount", nullable = false)
    private int refundAmount;

    @Builder.Default
    @Column(name = "refund_date", nullable = true)
    private LocalDateTime refundDate = LocalDateTime.now();

    // PaymentCreateRequestDto에서 결제 수단만 받음
    public Payment(PaymentCreateRequestDto requestDto) {
        this.paymentMethod = requestDto.getPaymentMethod();
        this.status = getStatus();
        //this.paymentAmount = 0;  // 결제 금액은 추후 주문 총액을 받아옴
    }

    @PrePersist
    public void setDefaultStatus() {
        if (this.status == null) {
            this.status = PaymentStatus.PENDING;
        }
    }

    public void setOrder(Order order) {
        this.order = new Order();  // 기존의 주문 객체를 참조로 설정하거나
    }

    // 결제 상태 업데이트
    public void updateStatus(PaymentStatus status) {
        this.status = status;
    }

    // 결제 성공 때 추가 반환 정보
    public void setAdditionalPaymentInfo(UUID id, String paymentKey, LocalDateTime paymentDate) {
        this.id = id;
        this.paymentKey = paymentKey;
        this.paymentDate = paymentDate;
    }

    // 결제 취소 때 환불 정보
    public void setRefund(UUID id, String refundMethod, int refundAmount, LocalDateTime refundDate) {
        this.id = id;
        this.refundMethod = refundMethod;
        this.refundAmount = refundAmount;
        this.refundDate = refundDate;
    }

    // 소프트 삭제
    public void changeToDeleted() {
        this.isDeleted = true;
    }

    // 추후 리팩토링으로 추가 예정
//    @Column(name = "failure_reason")
//    private String failureReason;   // 결제 실패시 이유(잔액부족, 은행시스템이나 카드사 점검중, 정지된 카드 등등)

}


