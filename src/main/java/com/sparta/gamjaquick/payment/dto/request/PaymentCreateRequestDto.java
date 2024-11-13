package com.sparta.gamjaquick.payment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
public class PaymentCreateRequestDto {

    private UUID orderId;
    private String paymentAmount;
    @Getter
    private String paymentKey;

}


