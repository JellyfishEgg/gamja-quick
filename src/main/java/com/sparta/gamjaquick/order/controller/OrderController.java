package com.sparta.gamjaquick.order.controller;

import com.sparta.gamjaquick.common.request.SearchParameter;
import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.common.response.PageResponseDto;
import com.sparta.gamjaquick.global.swagger.ApiErrorCodeExample;
import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.order.dto.request.OrderCreateRequestDto;
import com.sparta.gamjaquick.order.dto.request.OrderStatusUpdateRequestDto;
import com.sparta.gamjaquick.order.dto.response.OrderResponseDto;
import com.sparta.gamjaquick.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "주문 관련 API")
public class OrderController {

    private final OrderService orderService;

    // 주문 등록
    @PostMapping("")
    @ApiErrorCodeExample(ErrorCode.MENU_NOT_FOUND)
    @Operation(summary = "주문 생성", description = "주문을 생성 할 때 사용하는 API")
    public ApiResponseDto<OrderResponseDto> createOrder(@RequestBody @Valid OrderCreateRequestDto requestDto) {
        OrderResponseDto result = orderService.createOrder(requestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    // 주문 단건 조회
    @GetMapping("/{orderId}")
    @ApiErrorCodeExample(ErrorCode.RESOURCE_NOT_FOUND)
    @Operation(summary = "주문 단건 조회", description = "하나의 주문을 조회 할 때 사용하는 API")
    public ApiResponseDto<OrderResponseDto> getOrder(@PathVariable UUID orderId) {
        OrderResponseDto result = orderService.getOrder(orderId);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 주문 전체 조회 (페이징 처리 추가)
    @GetMapping("")
    @ApiErrorCodeExample(ErrorCode.INVALID_INPUT)
    @Operation(summary = "주문 전체 조회", description = "가게별, 사용자별 주문을 조회 할 때 사용하는 API")
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
    @ApiErrorCodeExample(ErrorCode.RESOURCE_NOT_FOUND)
    @Operation(summary = "주문 상태 업데이트", description = "주문을 취소하거나 상태를 업데이트 할 때 사용하는 API")
    public ApiResponseDto<OrderResponseDto> updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestBody @Valid OrderStatusUpdateRequestDto requestDto) {

        OrderResponseDto result = orderService.updateOrderStatus(orderId, requestDto);
        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    // 주문 소프트 삭제
    @DeleteMapping("/{orderId}")
    @ApiErrorCodeExample(ErrorCode.RESOURCE_NOT_FOUND)
    @Operation(summary = "주문 삭제", description = "주문을 삭제 할 때 사용하는 API")
    public ApiResponseDto<MessageType> deleteOrder(@PathVariable UUID orderId) {
        return ApiResponseDto.success(MessageType.DELETE);
    }
}
