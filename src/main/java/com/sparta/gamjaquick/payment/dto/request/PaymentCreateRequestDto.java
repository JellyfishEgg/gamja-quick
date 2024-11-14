package com.sparta.gamjaquick.payment.dto.request;

import com.sparta.gamjaquick.payment.entity.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class PaymentCreateRequestDto {

    private UUID orderId;
    //private int paymentAmount;
    private String paymentMethod;
    private PaymentStatus status;
    //private String paymentKey;

}


