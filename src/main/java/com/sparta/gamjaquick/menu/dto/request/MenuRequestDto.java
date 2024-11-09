package com.sparta.gamjaquick.menu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class MenuRequestDto {
    private String menuName;
    private String description;
    private Integer price;
    private Boolean isSoldOut;
}
