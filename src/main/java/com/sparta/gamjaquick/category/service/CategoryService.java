package com.sparta.gamjaquick.category.service;

import com.sparta.gamjaquick.category.dto.request.CreateCategoryRequestDto;
import com.sparta.gamjaquick.category.dto.response.CategoryResponseDto;
import com.sparta.gamjaquick.category.entity.Category;
import com.sparta.gamjaquick.category.repository.CategoryRepository;
import com.sparta.gamjaquick.common.request.SearchParameter;
import com.sparta.gamjaquick.common.response.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void create(CreateCategoryRequestDto requestDto) {

    }

    @Transactional
    public PageResponseDto<CategoryResponseDto> getCategoryList(SearchParameter searchParameter) {
        Page<Category> categories = categoryRepository.searchCategories(searchParameter);
        Page<CategoryResponseDto> result = categories.map(CategoryResponseDto::from);
        return PageResponseDto.of(result);
    }

}
