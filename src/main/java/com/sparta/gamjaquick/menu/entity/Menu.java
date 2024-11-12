package com.sparta.gamjaquick.menu.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.menu.dto.request.MenuRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name="p_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends AuditingFields {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;


    //store 엔티티가 생기면 매핑 예정입니당
    private UUID storeId;

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isSoldOut;


    public Menu(UUID storeId, MenuRequestDto menuRequestDto) {
        this.storeId = storeId;
        this.name = menuRequestDto.getMenuName();
        this.description = menuRequestDto.getDescription();
        this.price = menuRequestDto.getPrice();
        this.isSoldOut = menuRequestDto.getIsSoldOut();
    }

    public void updateByMenuDto(MenuRequestDto menuRequestDto) {
        this.name = menuRequestDto.getMenuName();
        this.description = menuRequestDto.getDescription();
        this.price = menuRequestDto.getPrice();
        this.isSoldOut = menuRequestDto.getIsSoldOut();
        super.setUpdatedAt(LocalDateTime.now());
        //수정 한 사람 넣는 코드는 다시 작성
    }

    public void deleteMenu() {
        this.isDeleted = true;
        super.setDeletedAt(LocalDateTime.now());
        //삭제 한 사람 넣는 코드는 다시 작성
    }
}
