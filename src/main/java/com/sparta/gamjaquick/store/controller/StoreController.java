package com.sparta.gamjaquick.store.controller;

import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.store.dto.StoreResponseDto;
import com.sparta.gamjaquick.store.dto.request.CreateStoreRequestDto;
import com.sparta.gamjaquick.store.dto.response.CreateStoreResponseDto;
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
    public ApiResponseDto<?> registerStore(@RequestBody @Valid CreateStoreRequestDto requestDto) {
        CreateStoreResponseDto result = storeService.registerStore(requestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    // 가게 등록 승인(관리자)
    @PatchMapping("/{storeId}/approve")
    public ApiResponseDto<?> approveStore(@PathVariable("storeId") String storeId) {
        StoreResponseDto result = storeService.approveStore(storeId);
        return ApiResponseDto.success(MessageType.APPROVE, result);
    }

}
