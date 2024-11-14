package com.sparta.gamjaquick.payment.dto.request;

import com.sparta.gamjaquick.payment.entity.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentUpdateRequestDto {
    private PaymentStatus status;
}
