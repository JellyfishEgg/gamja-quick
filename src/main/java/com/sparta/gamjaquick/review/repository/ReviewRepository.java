package com.sparta.gamjaquick.review.repository;

import com.sparta.gamjaquick.review.entity.Review;
import com.sparta.gamjaquick.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Optional<Review> findByIdAndIsDeletedFalse(UUID id);
    List<Review> findByStoreIdAndIsDeletedFalse(UUID storeId);
    Optional<Review> findByOrderId(UUID orderId);
    List<Review> findByStoreAndIsDeletedFalse(Store store);
}