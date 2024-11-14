package com.sparta.gamjaquick.category.controller;

import com.sparta.gamjaquick.category.dto.request.CategoryRequestDto;
import com.sparta.gamjaquick.category.dto.response.CategoryResponseDto;
import com.sparta.gamjaquick.category.service.CategoryService;
import com.sparta.gamjaquick.common.request.SearchParameter;
import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.common.response.PageResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "카테고리 관련 API")
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 등록
    @PostMapping("")
    public ApiResponseDto<?> createCategory(@RequestBody @Valid CategoryRequestDto requestDto) {
        CategoryResponseDto result = categoryService.create(requestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    // 카테고리 목록 조회
    @GetMapping("")
    public ApiResponseDto<?> getCategoryList(@ModelAttribute SearchParameter searchParameter) {
        PageResponseDto<CategoryResponseDto> result = categoryService.getCategoryList(searchParameter);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 카테고리 수정
    @PutMapping("/{categoryId}")
    public ApiResponseDto<?> updateCategory(@PathVariable("categoryId") String categoryId,
                                            @RequestBody @Valid CategoryRequestDto requestDto) {
        CategoryResponseDto result = categoryService.update(categoryId, requestDto);
        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    // 카테고리 삭제
    @DeleteMapping("/{categoryId}")
    public ApiResponseDto<?> deleteCategory(@PathVariable("categoryId") String categoryId) {
        CategoryResponseDto result = categoryService.delete(categoryId);
        return ApiResponseDto.success(MessageType.DELETE, result);
    }

}
