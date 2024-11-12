package com.sparta.gamjaquick.category.service;

import com.sparta.gamjaquick.category.dto.request.CategoryRequestDto;
import com.sparta.gamjaquick.category.dto.response.CategoryResponseDto;
import com.sparta.gamjaquick.category.entity.Category;
import com.sparta.gamjaquick.category.repository.CategoryRepository;
import com.sparta.gamjaquick.common.request.SearchParameter;
import com.sparta.gamjaquick.common.response.PageResponseDto;
import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 카테고리 등록
    public CategoryResponseDto create(CategoryRequestDto requestDto) {
        // 카테고리가 이미 있는 경우
        categoryRepository.findByName(requestDto.getName()).ifPresent(category -> {
            throw new BusinessException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        });

        Category savedCategory = categoryRepository.save(Category.from(requestDto));
        return CategoryResponseDto.from(savedCategory);
    }

    // 카테고리 목록 조회
    @Transactional(readOnly = true)
    public PageResponseDto<CategoryResponseDto> getCategoryList(SearchParameter searchParameter) {
        Page<Category> categories = categoryRepository.searchCategories(searchParameter);
        Page<CategoryResponseDto> result = categories.map(CategoryResponseDto::from);
        return PageResponseDto.of(result);
    }

    // 카테고리 수정
    public CategoryResponseDto update(String categoryId, CategoryRequestDto requestDto) {
        Category findCategory = findById(categoryId);

        // 기존 이름과 동일한 경우 바로 리턴
        if (findCategory.getName().equals(requestDto.getName())) {
            return CategoryResponseDto.from(findCategory);
        }
        // 카테고리가 이미 있는 경우
        categoryRepository.findByName(requestDto.getName()).ifPresent(category -> {
            throw new BusinessException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        });

        findCategory.update(requestDto);
        return CategoryResponseDto.from(findCategory);
    }

    // 카테고리 삭제
    public CategoryResponseDto delete(String categoryId) {
        Category findCategory = findById(categoryId);

        // 이미 삭제된 카테고리일 경우 예외 처리
        if (findCategory.getIsDeleted()) {
            throw new BusinessException(ErrorCode.CATEGORY_ALREADY_DELETED);
        }

        findCategory.delete();
        return CategoryResponseDto.from(findCategory);
    }

    private Category findById(String categoryId) {
        return categoryRepository.findById(UUID.fromString(categoryId)).orElseThrow(
                () -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND)
        );
    }

}
