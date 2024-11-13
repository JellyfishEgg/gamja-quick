package com.sparta.gamjaquick.menu.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.menu.dto.request.MenuRequestDto;
import com.sparta.gamjaquick.menu.dto.response.MenuDeleteReponseDto;
import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_store_id" ,insertable = false, updatable = false)
    private Store store;

    @Column(name= "p_store_id")
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


    public Menu(Store store, MenuRequestDto menuRequestDto) {
        this.store = store;
        this.name = menuRequestDto.getMenuName();
        this.description = menuRequestDto.getDescription();
        this.price = menuRequestDto.getPrice();
        this.isSoldOut = menuRequestDto.getIsSoldOut();
        this.isDeleted = false;
    }

    public void updateByMenuDto(MenuRequestDto menuRequestDto) {
        this.name = menuRequestDto.getMenuName();
        this.description = menuRequestDto.getDescription();
        this.price = menuRequestDto.getPrice();
        this.isSoldOut = menuRequestDto.getIsSoldOut();
    }

    public MenuDeleteReponseDto deleteMenu(String auditingUser) {
        this.isDeleted = true;
        super.delete(auditingUser);
        return new MenuDeleteReponseDto(this.id, super.getDeletedAt(),super.getDeletedBy());
    }
}
