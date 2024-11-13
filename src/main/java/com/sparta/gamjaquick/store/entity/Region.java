package com.sparta.gamjaquick.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Region {

    @Column(length = 20)
    @Comment("시/도")
    private String sido;

    @Column(length = 20)
    @Comment("시/군/구")
    private String sigungu;

    @Embedded
    private RoadAddress roadAddress;

    @Embedded
    private JibunAddress jibunAddress;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class RoadAddress {

        @Column(length = 50)
        @Comment("도로명")
        private String roadName;

        @Column(length = 20)
        @Comment("건물번호")
        private String buildingNumber;

        @Column(length = 50)
        @Comment("건물명")
        private String buildingName;

        @Column(length = 100)
        @Comment("상세주소")
        private String detailAddress;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class JibunAddress {

        @Column(length = 20)
        @Comment("동/읍/면")
        private String dong;

        @Column(length = 20)
        @Comment("지번")
        private String jibun;
    }

}
