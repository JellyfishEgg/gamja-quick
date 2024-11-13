package com.sparta.gamjaquick.payment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class PaymentCreateRequestDto {

    private UUID orderId;
    private String paymentAmount;
}


