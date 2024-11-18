package com.sparta.gamjaquick.menu.dto.response;

import com.sparta.gamjaquick.menu.dto.request.MenuRequestDto;
import com.sparta.gamjaquick.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class MenuDeleteReponseDto {
    private UUID menuId;
    private LocalDateTime deletedAt;
    private String deletedBy;
}
