package com.sparta.gamjaquick.store.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.store.dto.request.StoreApprovalRequestDto;
import com.sparta.gamjaquick.store.dto.request.StoreCreateRequestDto;
import com.sparta.gamjaquick.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

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

    @Column(length = 100, nullable = false)
    @Comment("가게 이름")
    private String name;

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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public static Store from(User user, StoreCreateRequestDto dto) {
        Region.RoadAddress roadAddress = Region.RoadAddress.builder()
                .buildingNumber(dto.getBuildingNumber())
                .buildingName(dto.getBuildingName())
                .roadName(dto.getRoadName())
                .detailAddress(dto.getDetailAddress())
                .build();

        Region.JibunAddress jibunAddress = Region.JibunAddress.builder()
                .jibun(dto.getJibun())
                .dong(dto.getDong())
                .build();

        Region region = Region.builder()
                .sido(dto.getSido())
                .sigungu(dto.getSigungu())
                .roadAddress(roadAddress)
                .jibunAddress(jibunAddress)
                .build();

        return Store.builder()
                .user(user)
                .name(dto.getName())
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

}
