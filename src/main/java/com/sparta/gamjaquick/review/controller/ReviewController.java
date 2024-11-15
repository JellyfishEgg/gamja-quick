package com.sparta.gamjaquick.review.controller;

import com.sparta.gamjaquick.review.dto.request.ReviewRequestDto;
import com.sparta.gamjaquick.review.dto.response.ReviewResponseDto;
import com.sparta.gamjaquick.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Review", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/stores/{store_id}/orders/{order_id}")
    @Operation(summary = "리뷰 작성", description = "리뷰를 작성 할 때 사용하는 API")
    public ReviewResponseDto createReview(
            @PathVariable("store_id") String storeId,
            @PathVariable("order_id") String orderId,
            @RequestParam Long userId,
            @RequestBody ReviewRequestDto reviewRequestDto) {
        return reviewService.createReview(storeId, orderId, userId, reviewRequestDto);
    }

    @PutMapping("/{review_id}")
    @Operation(summary = "리뷰 수정", description = "리뷰를 수정 할 때 사용하는 API")
    public ReviewResponseDto updateReview(
            @PathVariable("review_id") String reviewId,
            @RequestBody ReviewRequestDto reviewRequestDto) {
        return reviewService.updateReview(reviewId, reviewRequestDto);
    }

    @DeleteMapping("/{review_id}")
    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제 할 때 사용하는 API")
    public void deleteReview(
            @PathVariable("review_id") String reviewId,
            @RequestParam String deletedBy) {
        reviewService.deleteReview(reviewId, deletedBy);
    }

    @GetMapping("/{review_id}")
    @Operation(summary = "리뷰 단건 조회", description = "하나의 리뷰를 조회 할 때 사용하는 API")
    public ReviewResponseDto getReviewById(@PathVariable("review_id") String reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @GetMapping
    @Operation(summary = "리뷰 전체 조회", description = "가게별 리뷰 목록을 조회 할 때 사용하는 API")
    public List<ReviewResponseDto> getAllReviews() {
        return reviewService.getAllReviews();
    }
}