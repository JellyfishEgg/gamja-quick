package com.sparta.gamjaquick.review.controller;

import com.sparta.gamjaquick.review.dto.request.ReviewRequestDto;
import com.sparta.gamjaquick.review.dto.response.ReviewResponseDto;
import com.sparta.gamjaquick.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/stores/{storeId}/orders/{orderId}")
    @Parameters(
            {@Parameter(name = "storeId", description = "가게 ID", example = "c0a80018-9323-1fa9-8193-239fc7e00000")  ,
            @Parameter(name = "orderId", description = "주문 ID", example = "7f000001-9328-1505-8193-28353dc60000"),
            @Parameter(name = "userId", description = "유저 ID", example = "1")}
    )
    @Operation(summary = "리뷰 작성", description = "리뷰를 작성 할 때 사용하는 API")
    public ReviewResponseDto createReview(
            @PathVariable("storeId") String storeId,
            @PathVariable("orderId") String orderId,
            @RequestParam Long userId,
            @RequestBody ReviewRequestDto reviewRequestDto) {
        return reviewService.createReview(storeId, orderId, userId, reviewRequestDto);
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "리뷰를 수정 할 때 사용하는 API")
    @Parameter(name = "reviewId", description = "리뷰 ID", example = "c0a80018-9323-1fa9-8193-239fc7e00000")
    public ReviewResponseDto updateReview(
            @PathVariable("reviewId") String reviewId,
            @RequestBody ReviewRequestDto reviewRequestDto) {
        return reviewService.updateReview(reviewId, reviewRequestDto);
    }

    @DeleteMapping("/{reviewId}")
    @Parameters(
            {@Parameter(name = "reviewId", description = "리뷰 ID", example = "c0a80018-9323-1fa9-8193-239fc7e00000"),
            @Parameter(name = "deletedBy", description = "삭제한 유저", example = "고감자")}
    )
    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제 할 때 사용하는 API")
    public void deleteReview(
            @PathVariable("reviewId") String reviewId,
            @RequestParam String deletedBy) {
        reviewService.deleteReview(reviewId, deletedBy);
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "리뷰 단건 조회", description = "하나의 리뷰를 조회 할 때 사용하는 API")
    @Parameter(name = "reviewId", description = "리뷰 ID", example = "c0a80018-9323-1fa9-8193-239fc7e00000")
    public ReviewResponseDto getReviewById(@PathVariable("reviewId") String reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @GetMapping
    @Operation(summary = "리뷰 전체 조회", description = "가게별 리뷰 목록을 조회 할 때 사용하는 API")
    public List<ReviewResponseDto> getAllReviews() {
        return reviewService.getAllReviews();
    }

}
