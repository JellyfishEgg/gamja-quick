package com.sparta.gamjaquick.order.controller;

import com.sparta.gamjaquick.common.request.SearchParameter;
import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.common.response.PageResponseDto;
import com.sparta.gamjaquick.order.dto.request.OrderCreateRequestDto;
import com.sparta.gamjaquick.order.dto.request.OrderStatusUpdateRequestDto;
import com.sparta.gamjaquick.order.dto.response.OrderResponseDto;
import com.sparta.gamjaquick.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 등록
    @PostMapping("")
    public ApiResponseDto<OrderResponseDto> createOrder(@RequestBody @Valid OrderCreateRequestDto requestDto) {
        OrderResponseDto result = orderService.createOrder(requestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    // 주문 단건 조회
    @GetMapping("/{orderId}")
    public ApiResponseDto<OrderResponseDto> getOrder(@PathVariable UUID orderId) {
        OrderResponseDto result = orderService.getOrder(orderId);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 주문 전체 조회 (페이징 처리 추가)
    @GetMapping("")
    public ApiResponseDto<PageResponseDto<OrderResponseDto>> getAllOrders(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) UUID storeId,
            @ModelAttribute SearchParameter searchParameter) {

        Pageable pageable = searchParameter.getPageable();
        PageResponseDto<OrderResponseDto> result = orderService.getAllOrders(userId, storeId, pageable);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 주문 상태 업데이트 (주문 취소 포함)
    @PutMapping("/{orderId}/status")
    public ApiResponseDto<OrderResponseDto> updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestBody @Valid OrderStatusUpdateRequestDto requestDto) {

        OrderResponseDto result = orderService.updateOrderStatus(orderId, requestDto);
        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    // 주문 소프트 삭제
    @DeleteMapping("/{orderId}")
    public ApiResponseDto<MessageType> deleteOrder(@PathVariable UUID orderId) {
        return ApiResponseDto.success(MessageType.DELETE);
    }
}
