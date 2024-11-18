package com.sparta.gamjaquick.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuRequestDto {

    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    private String menuName;
    private String description;
    private Integer price;
    private Boolean isSoldOut;
}
