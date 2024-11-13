package com.sparta.gamjaquick.store.dto.response;

import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.store.entity.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class StoreResponseDto {

    private String id;
    private String name;

    private String phoneNumber;
    private StoreStatus storeStatus;
    private String rejectionReason;
    private double rating;

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

    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static StoreResponseDto from(Store store) {
        return StoreResponseDto.builder()
                .id(store.getId().toString())
                .name(store.getName())
                .phoneNumber(store.getPhoneNumber())
                .storeStatus(store.getStoreStatus())
                .rejectionReason(store.getRejectionReason())
                .rating(store.getRating())
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
                .isDeleted(store.getIsDeleted())
                .createdAt(store.getCreatedAt())
                .updatedAt(store.getUpdatedAt())
                .build();
    }

}
