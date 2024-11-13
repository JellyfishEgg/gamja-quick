package com.sparta.gamjaquick.store.controller;

import com.sparta.gamjaquick.common.request.StoreSearchParameter;
import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.common.response.PageResponseDto;
import com.sparta.gamjaquick.store.dto.request.StoreApprovalRequestDto;
import com.sparta.gamjaquick.store.dto.request.StoreCreateRequestDto;
import com.sparta.gamjaquick.store.dto.response.StoreCreateResponseDto;
import com.sparta.gamjaquick.store.dto.response.StoreResponseDto;
import com.sparta.gamjaquick.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
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

}
