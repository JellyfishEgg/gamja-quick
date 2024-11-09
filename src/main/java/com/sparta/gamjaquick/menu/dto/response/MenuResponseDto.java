package com.sparta.gamjaquick.menu.dto.response;

import com.sparta.gamjaquick.menu.entity.Menu;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class MenuResponseDto{
    private UUID menuID;
    private String menuName;
    private String description;
    private Integer price;
    private UUID storeID;
    private Boolean isSoldOut;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;


    public MenuResponseDto(Menu menu) {
        this.menuID = menu.getId();
        this.menuName = menu.getName();
        this.description = menu.getDescription();
        this.price = menu.getPrice();
        this.storeID = menu.getStoreId();
        this.isSoldOut = menu.getIsSoldOut();
        this.createdAt = menu.getCreatedAt();
        this.updatedAt = menu.getUpdatedAt();
        this.isDeleted = menu.getIsDeleted();
    }
}
