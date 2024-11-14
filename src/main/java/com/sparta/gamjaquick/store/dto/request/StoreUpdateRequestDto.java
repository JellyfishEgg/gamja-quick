package com.sparta.gamjaquick.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class StoreUpdateRequestDto {

    @NotBlank(message = "카테고리 ID를 입력해 주세요.")
    private String categoryId;

    private String imageUrl;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다. (예: 010-1234-5678)")
    private String phoneNumber;

}
