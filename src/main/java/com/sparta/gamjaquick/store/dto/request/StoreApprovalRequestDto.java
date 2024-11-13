package com.sparta.gamjaquick.store.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class StoreApprovalRequestDto {

    private boolean approved;

    @Size(max = 500, message = "거절 사유는 500자를 초과할 수 없습니다.")
    private String rejectionReason;

}
