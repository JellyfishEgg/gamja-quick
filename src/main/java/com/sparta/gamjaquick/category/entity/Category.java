package com.sparta.gamjaquick.category.entity;

import com.sparta.gamjaquick.category.dto.request.CategoryRequestDto;
import com.sparta.gamjaquick.common.AuditingFields;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_cateogry")
@Entity
public class Category extends AuditingFields {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    private String name;

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

    public void delete() {
        this.isDeleted = true;
    }

}