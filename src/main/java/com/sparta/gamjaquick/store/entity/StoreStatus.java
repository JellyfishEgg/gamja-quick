package com.sparta.gamjaquick.store.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreStatus {

    PENDING_APPROVAL("승인 대기 중"),
    APPROVED("승인"),
    REJECTED("승인 거부"),
    OPEN("영업 중"),
    CLOSE("영업 종료"),
    TEMPORARILY_CLOSED("임시 휴업"),
    PERMANENTLY_CLOSED("폐업"),
    SUSPENDED("영업 정지");

    private final String description;

}
