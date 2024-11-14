package com.sparta.gamjaquick.review.service;

import com.sparta.gamjaquick.review.dto.request.ReviewRequestDto;
import com.sparta.gamjaquick.review.dto.response.ReviewResponseDto;
import com.sparta.gamjaquick.review.entity.Review;
import com.sparta.gamjaquick.review.repository.ReviewRepository;
import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.store.repository.StoreRepository;
import com.sparta.gamjaquick.user.entity.User;
import com.sparta.gamjaquick.user.repository.UserRepository;
import com.sparta.gamjaquick.order.entity.Order;
import com.sparta.gamjaquick.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            StoreRepository storeRepository,
            UserRepository userRepository,
            OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public ReviewResponseDto createReview(String storeId, String orderId, Long userId, ReviewRequestDto reviewRequestDto) {
        UUID storeUUID = UUID.fromString(storeId);
        Store store = storeRepository.findById(storeUUID)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));

        Order order = orderRepository.findById(UUID.fromString(orderId))
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Review review = new Review(
                UUID.randomUUID().toString(),
                store,
                order,
                user,
                reviewRequestDto.getRating(),
                reviewRequestDto.getContent(),
                reviewRequestDto.getIsHidden()
        );
        reviewRepository.save(review);

        return new ReviewResponseDto(
                review.getId(),
                review.getStore().getId().toString(),
                review.getOrder().getId().toString(),
                review.getUser().getUsername(),
                review.getRating(),
                review.getContent(),
                review.getIsHidden(),
                review.getCreatedAt(),
                review.getUpdatedAt(),
                review.getIsDeleted()
        );
    }

    @Override
    public ReviewResponseDto updateReview(String reviewId, ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.findByIdAndIsDeletedFalse(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        review.updateReview(reviewRequestDto.getRating(), reviewRequestDto.getContent(), reviewRequestDto.getIsHidden());
        reviewRepository.save(review);

        return new ReviewResponseDto(
                review.getId(),
                review.getStore().getId().toString(),
                review.getOrder().getId().toString(),
                review.getUser().getUsername(),
                review.getRating(),
                review.getContent(),
                review.getIsHidden(),
                review.getCreatedAt(),
                review.getUpdatedAt(),
                review.getIsDeleted()
        );
    }

    @Override
    public void deleteReview(String reviewId, String deletedBy) {
        Review review = reviewRepository.findByIdAndIsDeletedFalse(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        review.deleteReview(deletedBy);
        reviewRepository.save(review);
    }

    @Override
    public ReviewResponseDto getReviewById(String reviewId) {
        Review review = reviewRepository.findByIdAndIsDeletedFalse(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        return new ReviewResponseDto(
                review.getId(),
                review.getStore().getId().toString(),
                review.getOrder().getId().toString(),
                review.getUser().getUsername(),
                review.getRating(),
                review.getContent(),
                review.getIsHidden(),
                review.getCreatedAt(),
                review.getUpdatedAt(),
                review.getIsDeleted()
        );
    }

    @Override
    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .filter(review -> !review.getIsDeleted())
                .map(review -> new ReviewResponseDto(
                        review.getId(),
                        review.getStore().getId().toString(),
                        review.getOrder().getId().toString(),
                        review.getUser().getUsername(),
                        review.getRating(),
                        review.getContent(),
                        review.getIsHidden(),
                        review.getCreatedAt(),
                        review.getUpdatedAt(),
                        review.getIsDeleted()
                ))
                .collect(Collectors.toList());
    }
}