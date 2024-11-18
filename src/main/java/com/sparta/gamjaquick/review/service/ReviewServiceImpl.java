package com.sparta.gamjaquick.review.service;

import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.global.error.exception.BusinessException;
import com.sparta.gamjaquick.order.entity.Order;
import com.sparta.gamjaquick.order.repository.OrderRepository;
import com.sparta.gamjaquick.review.dto.request.ReviewRequestDto;
import com.sparta.gamjaquick.review.dto.response.ReviewResponseDto;
import com.sparta.gamjaquick.review.entity.Review;
import com.sparta.gamjaquick.review.repository.ReviewRepository;
import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.store.service.StoreService;
import com.sparta.gamjaquick.user.entity.User;
import com.sparta.gamjaquick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreService storeService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public ReviewResponseDto createReview(String storeId, String orderId, Long userId, ReviewRequestDto reviewRequestDto) {
        Store store = storeService.findById(storeId);

        Order order = orderRepository.findById(UUID.fromString(orderId))
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Review review = new Review(
                store,
                order,
                user,
                reviewRequestDto.getRating(),
                reviewRequestDto.getContent(),
                reviewRequestDto.getIsHidden()
        );
        reviewRepository.save(review);

        // 해당 가게의 평점 업데이트
        updateStoreRating(review);

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

    @Transactional
    @Override
    public ReviewResponseDto updateReview(String reviewId, ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.findByIdAndIsDeletedFalse(UUID.fromString(reviewId))
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

        review.updateReview(reviewRequestDto.getRating(), reviewRequestDto.getContent(), reviewRequestDto.getIsHidden());
        reviewRepository.save(review);

        // 해당 가게의 평점 업데이트
        updateStoreRating(review);

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

    @Transactional
    @Override
    public void deleteReview(String reviewId, String deletedBy) {
        Review review = reviewRepository.findByIdAndIsDeletedFalse(UUID.fromString(reviewId))
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

        review.deleteReview(deletedBy);

        // 해당 가게의 평점 업데이트
        updateStoreRating(review);

        reviewRepository.save(review);
    }

    @Override
    public ReviewResponseDto getReviewById(String reviewId) {
        Review review = reviewRepository.findByIdAndIsDeletedFalse(UUID.fromString(reviewId))
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

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

    // 가게의 평균 평점을 구하는 로직
    private void updateStoreRating(Review review) {
        Store store = review.getStore();

        // 평점 평균 계산
        List<Review> reviews = reviewRepository.findByStoreAndIsDeletedFalse(store);
        double averageRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        // 변경된 값 저장
        store.updateRating(averageRating);
    }

}
