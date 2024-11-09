package com.sparta.gamjaquick.menu.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.menu.dto.request.MenuRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="p_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends AuditingFields {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;


    //store 엔티티가 생기면 매핑 예정입니당
    private UUID storeId;

    @Column(nullable = false)
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

    }

    public void deleteMenu() {
        this.isDeleted = true;
    }
}
