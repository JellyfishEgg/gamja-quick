package com.sparta.gamjaquick.store.entity;

import com.sparta.gamjaquick.category.entity.Category;
import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.menu.entity.Menu;
import com.sparta.gamjaquick.store.dto.request.StoreApprovalRequestDto;
import com.sparta.gamjaquick.store.dto.request.StoreCreateRequestDto;
import com.sparta.gamjaquick.store.dto.request.StoreUpdateRequestDto;
import com.sparta.gamjaquick.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_stores", indexes = {
        @Index(name = "idx_sido_sigungu", columnList = "sido,sigungu"),
        @Index(name = "idx_sigungu", columnList = "sigungu")
})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @Comment(value = "카테고리 ID")
    private Category category;

    @Column(length = 100, nullable = false)
    @Comment("가게 이름")
    private String name;

    @Comment("가게 이미지")
    private String imageUrl;

    @Column(length = 200, nullable = false)
    @Comment("가게 주소")
    private String address;

    @Column(length = 200, nullable = false)
    @Comment("가게 지번 주소")
    private String jibunAddress;

    @Embedded
    private Region region;

    @Column(length = 20, nullable = false)
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

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Menu> menuList = new ArrayList<>();

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public static Store from(User user, Category category, StoreCreateRequestDto dto) {
        Region region = Region.from(dto);

        return Store.builder()
                .user(user)
                .category(category)
                .name(dto.getName())
                .imageUrl(dto.getImageUrl())
                .address(dto.getRoadAddress())
                .jibunAddress(dto.getJibunAddress())
                .phoneNumber(dto.getPhoneNumber())
                .region(region)
                .storeStatus(StoreStatus.PENDING_APPROVAL)
                .build();
    }

    /**
     * 가게가 승인 대기 상태인지 확인합니다.
     * @return 승인 대기 상태이면 true, 그렇지 않으면 false
     */
    public boolean isAwaitingApproval() {
        return this.storeStatus == StoreStatus.PENDING_APPROVAL
                || this.storeStatus == StoreStatus.REJECTED;
    }

    /**
     * 가게 등록 승인 or 반려
     */
    public void processApproval(StoreApprovalRequestDto requestDto) {
        if (this.storeStatus == StoreStatus.PENDING_APPROVAL
                || this.storeStatus == StoreStatus.REJECTED) {

            if (requestDto.isApproved()) {
                this.storeStatus = StoreStatus.APPROVED;
                this.rejectionReason = "관리자 승인 완료";
            } else {
                this.storeStatus = StoreStatus.REJECTED;
                this.rejectionReason = requestDto.getRejectionReason();
            }
        }
    }

    /**
     * 가게 수정 (가게 이미지, 전화번호, 카테고리만 수정 가능)
     */
    public void update(StoreUpdateRequestDto requestDto, Category category) {
        this.imageUrl = requestDto.getImageUrl();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.category = category;
    }

    /**
     * 가게 삭제
     */
    public void delete(String auditingUser) {
        this.isDeleted = true;
        this.storeStatus = StoreStatus.PERMANENTLY_CLOSED;
        super.delete(auditingUser);
    }

    public Store(UUID id){
        this.id = id;
    }
}
