package com.sparta.gamjaquick.review.repository;

import com.sparta.gamjaquick.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Optional<Review> findByIdAndIsDeletedFalse(String id);
    List<Review> findByStoreIdAndIsDeletedFalse(String storeId);
    Optional<Review> findByOrderId(String orderId);
}