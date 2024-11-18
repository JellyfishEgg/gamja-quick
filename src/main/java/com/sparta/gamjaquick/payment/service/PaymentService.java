package com.sparta.gamjaquick.payment.service;


import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.global.error.exception.BusinessException;
import com.sparta.gamjaquick.payment.dto.request.PaymentCreateRequestDto;
import com.sparta.gamjaquick.payment.dto.request.PaymentUpdateRequestDto;
import com.sparta.gamjaquick.payment.dto.response.PaymentResponseDto;
import com.sparta.gamjaquick.payment.entity.Payment;
import com.sparta.gamjaquick.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    // 결제 생성
    public PaymentResponseDto createPayment(PaymentCreateRequestDto paymentRequestDto, int totalPrice) {
        Payment payment = paymentRepository.save(new Payment(paymentRequestDto, totalPrice));
        return PaymentResponseDto.from(payment);
    }

    // ID로 조회(고객이나 사장님은 본인이 이용하는 카드사에서 직접 확인하고, 이 조회기능은 관리자용..?)
    @Transactional(readOnly = true)
    public PaymentResponseDto getPayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow (() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        return PaymentResponseDto.from(payment);
    }

    // 결제 상태 업데이트
    public PaymentResponseDto updatePaymentStatus(UUID paymentId, PaymentUpdateRequestDto paymentRequestDto) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        payment.updateStatus(paymentRequestDto.getStatus());
        return PaymentResponseDto.from(payment);
    }

    // 결제 삭제
    public void deletePayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        payment.changeToDeleted();
    }

}