package com.sparta.gamjaquick.review.service;

import com.sparta.gamjaquick.review.dto.request.ReviewRequestDto;
import com.sparta.gamjaquick.review.dto.response.ReviewResponseDto;
import com.sparta.gamjaquick.user.entity.User;

import java.util.List;

public interface ReviewService {
    ReviewResponseDto createReview(String storeId, String orderId, User user, ReviewRequestDto reviewRequestDto);
    ReviewResponseDto updateReview(String reviewId, ReviewRequestDto reviewRequestDto, User user);
    void deleteReview(String reviewId,User user);
    ReviewResponseDto getReviewById(String reviewId);
    List<ReviewResponseDto> getAllReviews();
}
