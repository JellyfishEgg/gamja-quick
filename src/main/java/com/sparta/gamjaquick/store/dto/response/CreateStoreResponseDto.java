package com.sparta.gamjaquick.store.dto.response;

import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.store.entity.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class CreateStoreResponseDto {

    private UUID id;
    private String name;

    private String phoneNumber;
    private StoreStatus storeStatus;  // 가게 상태 (예: PENDING_APPROVAL, APPROVED, REJECTED)

    // 주소 정보
    private String roadAddress;
    private String jibunAddress;
    private String sido;
    private String sigungu;
    private String roadName;
    private String buildingNumber;
    private String buildingName;
    private String detailAddress;
    private String dong;
    private String jibun;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CreateStoreResponseDto from(Store store) {
        return CreateStoreResponseDto.builder()
                .id(store.getId())
                .name(store.getName())
                .phoneNumber(store.getPhoneNumber())
                .storeStatus(store.getStoreStatus())
                .roadAddress(store.getAddress())
                .jibunAddress(store.getJibunAddress())
                .sido(store.getRegion().getSido())
                .sigungu(store.getRegion().getSigungu())
                .roadName(store.getRegion().getRoadAddress().getRoadName())
                .buildingNumber(store.getRegion().getRoadAddress().getBuildingNumber())
                .buildingName(store.getRegion().getRoadAddress().getBuildingName())
                .detailAddress(store.getRegion().getRoadAddress().getDetailAddress())
                .dong(store.getRegion().getJibunAddress().getDong())
                .jibun(store.getRegion().getJibunAddress().getJibun())
                .createdAt(store.getCreatedAt())
                .updatedAt(store.getUpdatedAt())
                .build();
    }

}
