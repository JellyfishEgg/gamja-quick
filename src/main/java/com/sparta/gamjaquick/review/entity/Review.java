package com.sparta.gamjaquick.review.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.order.entity.Order;
import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_review")
public class Review extends AuditingFields {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

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

    public Review(Store store, Order order, User user, int rating, String content, Boolean isHidden) {
//        this.id = id;
        this.store = store;
        this.order = order;
        this.user = user;
        this.rating = rating;
        this.content = content;
        this.isHidden = isHidden;
    }

    // 리뷰 내용 & 평점을 수정
    public void updateReview(int rating, String content, Boolean isHidden) {
        this.rating = rating;
        this.content = content;
        this.isHidden = isHidden;
    }

    // 리뷰 삭제
    public void deleteReview(String deletedBy) {
        this.isDeleted = true;
        this.setDeletedBy(deletedBy);
        this.setDeletedAt(java.time.LocalDateTime.now());
    }
}