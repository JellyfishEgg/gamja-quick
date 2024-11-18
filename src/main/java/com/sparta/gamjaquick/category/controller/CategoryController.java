package com.sparta.gamjaquick.category.controller;

import com.sparta.gamjaquick.category.dto.request.CategoryRequestDto;
import com.sparta.gamjaquick.category.dto.response.CategoryResponseDto;
import com.sparta.gamjaquick.category.service.CategoryService;
import com.sparta.gamjaquick.common.request.SearchParameter;
import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.common.response.PageResponseDto;
import com.sparta.gamjaquick.global.swagger.ApiErrorCodeExample;
import com.sparta.gamjaquick.global.swagger.ApiErrorCodeExamples;
import com.sparta.gamjaquick.global.error.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "카테고리 관련 API")
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 등록
    @PostMapping("")
    @ApiErrorCodeExample(ErrorCode.CATEGORY_ALREADY_EXISTS)
    @Operation(summary = "카테고리 등록", description = "카테고리를 등록 할 때 사용하는 API")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponseDto<?> createCategory(@RequestBody @Valid CategoryRequestDto requestDto) {
        CategoryResponseDto result = categoryService.create(requestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    // 카테고리 목록 조회
    @GetMapping("")
    @Operation(summary = "카테고리 조회", description = "카테고리를 조회 할 때 사용하는 API")
    public ApiResponseDto<?> getCategoryList(@ModelAttribute SearchParameter searchParameter) {
        PageResponseDto<CategoryResponseDto> result = categoryService.getCategoryList(searchParameter);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 카테고리 수정
    @ApiErrorCodeExamples({ErrorCode.CATEGORY_ALREADY_EXISTS,ErrorCode.CATEGORY_NOT_FOUND})
    @PutMapping("/{categoryId}")
    @Operation(summary = "카테고리 수정", description = "카테고리를 수정 할 때 사용하는 API")
    @Parameter(name = "categoryId", description = "카테고리 ID", example = "c0a80018-9323-1fa9-8193-239fc7e00000")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponseDto<?> updateCategory(@PathVariable("categoryId") String categoryId,
                                            @RequestBody @Valid CategoryRequestDto requestDto) {
        CategoryResponseDto result = categoryService.update(categoryId, requestDto);
        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    // 카테고리 삭제
    @DeleteMapping("/{categoryId}")
    @ApiErrorCodeExamples({ErrorCode.CATEGORY_NOT_FOUND, ErrorCode.CATEGORY_ALREADY_DELETED})
    @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제 할 때 사용하는 API")
    @Parameter(name = "categoryId", description = "카테고리 ID", example = "c0a80018-9323-1fa9-8193-239fc7e00000")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponseDto<?> deleteCategory(@PathVariable("categoryId") String categoryId) {
        CategoryResponseDto result = categoryService.delete(categoryId);
        return ApiResponseDto.success(MessageType.DELETE, result);
    }

}
