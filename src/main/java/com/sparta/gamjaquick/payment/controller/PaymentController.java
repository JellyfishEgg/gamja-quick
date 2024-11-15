package com.sparta.gamjaquick.payment.controller;

import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.payment.dto.request.PaymentCreateRequestDto;
import com.sparta.gamjaquick.payment.dto.request.PaymentUpdateRequestDto;
import com.sparta.gamjaquick.payment.dto.response.PaymentResponseDto;
import com.sparta.gamjaquick.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@Tag(name = "Payment", description = "결제 관련 API")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("")
    public ApiResponseDto<PaymentResponseDto> createPayment(@RequestBody @Valid PaymentCreateRequestDto requestDto) {
        PaymentResponseDto result = paymentService.createPayment(requestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    @GetMapping("/{paymentId}")
    public ApiResponseDto<PaymentResponseDto> getPayment(@PathVariable UUID paymentId) {
        PaymentResponseDto result = paymentService.getPayment(paymentId);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    @PutMapping("/{paymentId}")
    public ApiResponseDto<PaymentResponseDto> updatePaymentStatus(
            @PathVariable UUID paymentId,
            @RequestBody @Valid PaymentUpdateRequestDto requestDto) {
        PaymentResponseDto result = paymentService.updatePaymentStatus(paymentId, requestDto);
        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    @DeleteMapping("/{paymentId}")
    public ApiResponseDto<MessageType> deletePayment(@PathVariable UUID paymentId) {
        paymentService.deletePayment(paymentId);
        return ApiResponseDto.success(MessageType.DELETE);
    }
}