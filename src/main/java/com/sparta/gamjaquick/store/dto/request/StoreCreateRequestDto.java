package com.sparta.gamjaquick.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class StoreCreateRequestDto {

    @NotBlank(message = "카테고리 ID를 입력해 주세요.")
    private String categoryId;

    @NotBlank(message = "가게 이름을 입력해 주세요.")
    @Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하여야 합니다.")
    private String name;

    private String imageUrl;

    @NotBlank(message = "도로명 주소를 입력해 주세요.")
    private String roadAddress;

    @NotBlank(message = "지번 주소를 입력해 주세요.")
    private String jibunAddress;

    @NotBlank(message = "시/도를 입력해 주세요.")
    private String sido;

    @NotBlank(message = "시/군/구를 입력해 주세요.")
    private String sigungu;

    // 도로명 주소 정보
    @NotBlank(message = "도로명을 입력해 주세요.")
    private String roadName;

    @NotBlank(message = "건물 번호를 입력해 주세요.")
    private String buildingNumber;

    private String buildingName;

    private String detailAddress;

    // 지번 주소 정보
    private String dong;

    private String jibun;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다. (예: 010-1234-5678)")
    private String phoneNumber;

}
