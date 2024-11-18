package com.sparta.gamjaquick.payment.dto.response;

import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.global.error.exception.BusinessException;
import com.sparta.gamjaquick.payment.entity.Payment;
import com.sparta.gamjaquick.payment.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class PaymentResponseDto {
    private UUID id;                    // 결제 ID PK
    private UUID orderId;               // 주문 ID
    private int paymentAmount;          // 결제 금액
    private String paymentMethod;       // 결제 수단
    private LocalDateTime paymentDate;  // 결제 일시(감사로그와 용도구분)
    private PaymentStatus status;       // 결제 상태(성공/실패/취소)
    private String paymentKey;          // 결제사에서 결제 넘어가면 반환해주는 키private String refundMethod;
    private String refundMethod;        // 환불 수단
    private int refundAmount;           // 환불 금액
    private LocalDateTime refundDate;   // 환불 일시
    //private String failureReason;       // 결제가 실패시 이유(잔액부족, 은행시스템이나 카드사 점검중, 정지된 카드 등등)

    // order status 에 따라 반환되는 payment 값들 분리해서 dto 로 변환
    public static PaymentResponseDto from(Payment payment) {
        if (payment.getStatus() == PaymentStatus.PENDING) { // 주문 생성 때
            return PaymentResponseDto.builder()
                    .orderId(payment.getOrder().getId())
                    .paymentMethod(payment.getPaymentMethod())
                    .status(payment.getStatus())
                    .build();
            
        } else if (payment.getStatus() == PaymentStatus.SUCCESS) { // 주문 성공 때
            return PaymentResponseDto.builder()
                    .id(payment.getId())
                    .orderId(payment.getOrder().getId())
                    .paymentAmount(payment.getPaymentAmount())
                    .paymentMethod(payment.getPaymentMethod())
                    .paymentDate(payment.getPaymentDate())
                    .status(payment.getStatus())
                    .paymentKey(payment.getPaymentKey())
                    .build();
            
        } else if (payment.getStatus() == PaymentStatus.CANCELLED) { //주문 취소 때
            return PaymentResponseDto.builder()
                    .id(payment.getId())
                    .orderId(payment.getOrder().getId())
                    .status(payment.getStatus())
                    .paymentKey(payment.getPaymentKey())
                    .refundMethod(payment.getRefundMethod()) 
                    .refundAmount(payment.getRefundAmount())  
                    .refundDate(payment.getRefundDate())      
                    .build();
        }
        // 세 가지 경우가 아닌 경우 예외 처리
        throw new BusinessException(ErrorCode.INVALID_INPUT);
    }
}