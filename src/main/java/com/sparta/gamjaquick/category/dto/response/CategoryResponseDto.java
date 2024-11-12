package com.sparta.gamjaquick.category.dto.response;

import com.sparta.gamjaquick.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CategoryResponseDto {

    private String id;
    private String name;

    public static CategoryResponseDto from(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId().toString())
                .name(category.getName())
                .build();
    }
}
