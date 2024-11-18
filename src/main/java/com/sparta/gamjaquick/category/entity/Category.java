package com.sparta.gamjaquick.category.entity;

import com.sparta.gamjaquick.category.dto.request.CategoryRequestDto;
import com.sparta.gamjaquick.common.AuditingFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_category")
@Entity
public class Category extends AuditingFields {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Comment("카테고리 고유 ID")
    private UUID id;

    @Column(length = 100, nullable = false)
    @Comment("카테고리 이름")
    private String name;

    @Column(nullable = false)
    @Comment("삭제 여부")
    private boolean isDeleted;

    @Builder
    private Category(String name) {
        this.name = name;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public static Category from(CategoryRequestDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    public void update(CategoryRequestDto requestDto) {
        this.name = requestDto.getName();
    }

    public void delete(String auditingUser) {
        this.isDeleted = true;
        super.delete(auditingUser);
    }

}