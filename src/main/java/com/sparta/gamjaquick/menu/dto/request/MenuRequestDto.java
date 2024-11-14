package com.sparta.gamjaquick.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class MenuRequestDto {

    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    private String menuName;
    private String description;
    @NotBlank(message = "가격을 입력해주세요.")
    private Integer price;
    @NotBlank(message = "품절 여부를 입력해주세요.")
    private Boolean isSoldOut;
}
