package com.sparta.gamjaquick.review.service;

import com.sparta.gamjaquick.review.dto.request.ReviewRequestDto;
import com.sparta.gamjaquick.review.dto.response.ReviewResponseDto;
import java.util.List;

public interface ReviewService {
    ReviewResponseDto createReview(String storeId, String orderId, Long userId, ReviewRequestDto reviewRequestDto);
    ReviewResponseDto updateReview(String reviewId, ReviewRequestDto reviewRequestDto);
    void deleteReview(String reviewId, String deletedBy);
    ReviewResponseDto getReviewById(String reviewId);
    List<ReviewResponseDto> getAllReviews();
}