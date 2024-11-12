package com.sparta.gamjaquick.store.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_store")
@Entity
public class Store extends AuditingFields {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Comment("가게 고유 ID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Comment(value = "가게 소유자 ID")
    private User user;

    @Column(length = 100, nullable = false)
    @Comment("가게 이름")
    private String name;

    @Column(nullable = false)
    @Comment("가게 주소")
    private String address;

    @Column(length = 20)
    @Comment("가게 전화번호")
    private String phoneNumber;

    @Comment("가게 평점")
    private double rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("가게 운영 상태")
    private StoreStatus storeStatus = StoreStatus.PENDING_APPROVAL;

    @Column(length = 500)
    @Comment("승인 거부 사유")
    private String rejectionReason;

    @Column(nullable = false)
    @Comment("삭제 여부")
    private boolean isDeleted;

    public boolean getIsDeleted() {
        return isDeleted;
    }

}
