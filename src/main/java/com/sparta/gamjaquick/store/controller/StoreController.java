package com.sparta.gamjaquick.store.controller;

import com.sparta.gamjaquick.common.request.StoreSearchParameter;
import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.common.response.PageResponseDto;
import com.sparta.gamjaquick.store.dto.request.StoreApprovalRequestDto;
import com.sparta.gamjaquick.store.dto.request.StoreCreateRequestDto;
import com.sparta.gamjaquick.store.dto.request.StoreUpdateRequestDto;
import com.sparta.gamjaquick.store.dto.response.StoreCreateResponseDto;
import com.sparta.gamjaquick.store.dto.response.StoreResponseDto;
import com.sparta.gamjaquick.store.dto.response.StoreWithMenusResponseDto;
import com.sparta.gamjaquick.store.service.StoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
@Tag(name = "Store", description = "가게 관련 API")
public class StoreController {

    private final StoreService storeService;

    // 가게 등록 신청(가게 주인, 관리자인 경우는 바로 등록)
    @PostMapping("")
    public ApiResponseDto<?> registerStore(@RequestBody @Valid StoreCreateRequestDto requestDto) {
        StoreCreateResponseDto result = storeService.registerStore(requestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    // 가게 등록 승인(관리자)
    @PatchMapping("/{storeId}/approve")
    public ApiResponseDto<?> approveStore(@PathVariable("storeId") String storeId,
                                          @RequestBody @Valid StoreApprovalRequestDto requestDto) {
        StoreResponseDto result = storeService.approveStore(storeId, requestDto);
        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    // 가게 목록 조회
    @GetMapping("")
    public ApiResponseDto<?> getStoreList(@ModelAttribute StoreSearchParameter searchParameter) {
        PageResponseDto<StoreResponseDto> result = storeService.getStoreList(searchParameter);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 특정 가게 조회
    @GetMapping("/{storeId}")
    public ApiResponseDto<?> getStore(@PathVariable("storeId") String storeId) {
        StoreWithMenusResponseDto result = storeService.getStore(storeId);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 가게 수정
    @PutMapping("/{storeId}")
    public ApiResponseDto<?> updateStore(@PathVariable("storeId") String storeId,
                                         @RequestBody @Valid StoreUpdateRequestDto requestDto) {
        StoreResponseDto result = storeService.update(storeId, requestDto);
        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    // 가게 삭제
    @DeleteMapping("/{storeId}")
    public ApiResponseDto<?> deleteStore(@PathVariable("storeId") String storeId) {
        StoreResponseDto result = storeService.delete(storeId);
        return ApiResponseDto.success(MessageType.DELETE, result);
    }

}
