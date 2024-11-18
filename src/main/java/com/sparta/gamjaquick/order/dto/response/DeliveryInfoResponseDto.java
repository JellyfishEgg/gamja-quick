package com.sparta.gamjaquick.order.dto.response;

import com.sparta.gamjaquick.order.entity.DeliveryInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryInfoResponseDto {

    private String address;  // 배달 주소
    private String request;  // 배달 요청 사항

    // DeliveryInfo 엔티티를 DeliveryInfoDto로 변환
    public static DeliveryInfoResponseDto from(DeliveryInfo deliveryInfo) {
        return DeliveryInfoResponseDto.builder()
                .address(deliveryInfo.getAddress())
                .request(deliveryInfo.getRequest())
                .build();
    }
}
