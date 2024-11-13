package com.sparta.gamjaquick.common.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoreSearchParameter extends SearchParameter {

    private String categoryId; // 카테고리 UUID

}
