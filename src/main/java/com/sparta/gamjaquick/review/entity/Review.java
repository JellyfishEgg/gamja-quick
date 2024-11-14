package com.sparta.gamjaquick.review.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.user.entity.User;
import com.sparta.gamjaquick.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "p_review")
public class Review extends AuditingFields {

    @Id
    @Column(length = 100)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int rating;

    @Column(length = 100)
    private String content;

    @Column(nullable = false)
    private Boolean isHidden = true;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    private String createdBy;
    private String updatedBy;
    private String deletedBy;
    private LocalDateTime deletedAt;

    public void updateReview(int rating, String content, Boolean isHidden) {
        this.rating = rating;
        this.content = content;
        this.isHidden = isHidden;
    }

    public void deleteReview(String deletedBy) {
        this.isDeleted = true;
        this.deletedBy = deletedBy;
        this.deletedAt = LocalDateTime.now();
    }
}