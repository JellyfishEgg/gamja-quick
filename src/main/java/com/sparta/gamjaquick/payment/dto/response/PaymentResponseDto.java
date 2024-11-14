package com.sparta.gamjaquick.payment.dto.response;

import com.sparta.gamjaquick.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class PaymentResponseDto {
    private UUID id;                  // 결제 ID (Primary Key)
    private UUID orderId;               // 주문 ID
    private String paymentAmount;       // 결제 금액
    private LocalDateTime paymentDate;  // 결제 일시(감사로그와 용도구분)
    private String status;              // 결제 상태(성공/실패)
    private String paymentKey;          // 결제사에서 결제 넘어가면 반환해주는 키
    //private String failureReason;       // 결제가 실패시 이유(잔액부족, 은행시스템이나 카드사 점검중, 정지된 카드 등등)

    public static PaymentResponseDto from(Payment payment) {
        return PaymentResponseDto.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .paymentAmount(payment.getPaymentAmount())
                .paymentDate(payment.getPaymentDate())
                .status(payment.getStatus().name())
                .paymentKey(payment.getPaymentKey())
                .build();
    }
}