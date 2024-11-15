package com.sparta.gamjaquick.payment.controller;

import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.global.swagger.ApiErrorCodeExample;
import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.payment.dto.request.PaymentCreateRequestDto;
import com.sparta.gamjaquick.payment.dto.request.PaymentUpdateRequestDto;
import com.sparta.gamjaquick.payment.dto.response.PaymentResponseDto;
import com.sparta.gamjaquick.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "결제 생성", description = "결제를 생성 할 때 사용하는 API")
    public ApiResponseDto<PaymentResponseDto> createPayment(@RequestBody @Valid PaymentCreateRequestDto requestDto) {
        PaymentResponseDto result = paymentService.createPayment(requestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    @GetMapping("/{paymentId}")
    @ApiErrorCodeExample(ErrorCode.RESOURCE_NOT_FOUND)
    @Operation(summary = "결제 내역 조회", description = "결제 내역을 조회 할 때 사용하는 API")
    public ApiResponseDto<PaymentResponseDto> getPayment(@PathVariable UUID paymentId) {
        PaymentResponseDto result = paymentService.getPayment(paymentId);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    @PutMapping("/{paymentId}")
    @ApiErrorCodeExample(ErrorCode.RESOURCE_NOT_FOUND)
    @Operation(summary = "결제 수정", description = "결제를 수정 할 때 사용하는 API")
    public ApiResponseDto<PaymentResponseDto> updatePaymentStatus(
            @PathVariable UUID paymentId,
            @RequestBody @Valid PaymentUpdateRequestDto requestDto) {
        PaymentResponseDto result = paymentService.updatePaymentStatus(paymentId, requestDto);
        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    @DeleteMapping("/{paymentId}")
    @ApiErrorCodeExample(ErrorCode.RESOURCE_NOT_FOUND)
    @Operation(summary = "결제 취소", description = "결제를 취소 할 때 사용하는 API")
    public ApiResponseDto<MessageType> deletePayment(@PathVariable UUID paymentId) {
        paymentService.deletePayment(paymentId);
        return ApiResponseDto.success(MessageType.DELETE);
    }
}