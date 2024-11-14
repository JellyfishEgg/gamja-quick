CREATE TABLE p_stores
(
    id               BINARY(16) NOT NULL COMMENT '가게 고유 ID',
    user_id          BIGINT COMMENT '가게 소유자 ID',
    category_id      BINARY(16) NOT NULL COMMENT '카테고리 고유 ID',
    name             VARCHAR(100) NOT NULL COMMENT '가게 이름',
    image_url        VARCHAR(255) COMMENT '가게 이미지',
    address          VARCHAR(200) NOT NULL COMMENT '가게 주소',
    jibun_address    VARCHAR(200) NOT NULL COMMENT '가게 지번 주소',
    phone_number     VARCHAR(20)  NOT NULL COMMENT '가게 전화번호',
    rating           FLOAT(53)    NOT NULL COMMENT '가게 평점' DEFAULT 0,
    store_status     ENUM ('APPROVED','CLOSE','OPEN','PENDING_APPROVAL','PERMANENTLY_CLOSED','REJECTED','SUSPENDED','TEMPORARILY_CLOSED') NOT NULL COMMENT '가게 운영 상태',
    rejection_reason VARCHAR(500) COMMENT '승인 거부 사유',
    sido             VARCHAR(20) COMMENT '시/도',
    sigungu          VARCHAR(20) COMMENT '시/군/구',
    road_name        VARCHAR(50) COMMENT '도로명',
    building_name    VARCHAR(50) COMMENT '건물명',
    building_number  VARCHAR(20) COMMENT '건물번호',
    dong             VARCHAR(20) COMMENT '동/읍/면',
    jibun            VARCHAR(20) COMMENT '지번',
    detail_address   VARCHAR(100) COMMENT '상세주소',
    is_deleted       BIT          NOT NULL COMMENT '삭제 여부',
    created_at       DATETIME(6) NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    updated_at       DATETIME(6),
    updated_by       VARCHAR(255),
    deleted_at       DATETIME(6),
    deleted_by       VARCHAR(255),
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE p_stores
    ADD CONSTRAINT FKt0cqa7pyqclhoh8o7nt15a2bm
        FOREIGN KEY (user_id)
            REFERENCES p_users (id);

ALTER TABLE p_stores
    ADD CONSTRAINT FK8sxoo6ukpp6kqaf4ylsqfbld5
        FOREIGN KEY (category_id)
            REFERENCES p_category (id);

CREATE INDEX idx_sido_sigungu
    ON p_stores (sido, sigungu);

CREATE INDEX idx_sigungu
    ON p_stores (sigungu);