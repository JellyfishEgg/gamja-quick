package com.sparta.gamjaquick.menu.dto.response;

import com.sparta.gamjaquick.menu.dto.request.MenuRequestDto;
import com.sparta.gamjaquick.menu.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class MenuDeleteReponseDto {
    private UUID menuId;
    private LocalDateTime deletedAt;
    private String deletedBy;

    public MenuDeleteReponseDto(UUID id, LocalDateTime deletedAt, String deletedBy) {
        this.menuId = id;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
    }
}
