package com.sparta.gamjaquick.store.dto.response;

import com.sparta.gamjaquick.menu.dto.response.MenuResponseDto;
import com.sparta.gamjaquick.menu.entity.Menu;
import com.sparta.gamjaquick.store.entity.Store;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreWithMenusResponseDto extends StoreResponseDto {

    private final List<MenuResponseDto> menuList;

    private StoreWithMenusResponseDto(StoreResponseDto storeResponseDto, List<MenuResponseDto> menuList) {
        super(storeResponseDto.getId(), storeResponseDto.getName(), storeResponseDto.getPhoneNumber(),
                storeResponseDto.getStoreStatus(), storeResponseDto.getRejectionReason(), storeResponseDto.getRating(),
                storeResponseDto.getRoadAddress(), storeResponseDto.getJibunAddress(), storeResponseDto.getSido(),
                storeResponseDto.getSigungu(), storeResponseDto.getRoadName(), storeResponseDto.getBuildingNumber(),
                storeResponseDto.getBuildingName(), storeResponseDto.getDetailAddress(), storeResponseDto.getDong(),
                storeResponseDto.getJibun(), storeResponseDto.isDeleted(), storeResponseDto.getCreatedAt(),
                storeResponseDto.getUpdatedAt());
        this.menuList = menuList;
    }

    public static StoreWithMenusResponseDto from(Store store, List<Menu> menuList) {
        StoreResponseDto from = StoreResponseDto.from(store);
        List<MenuResponseDto> menuResponseDtoList = menuList.stream().map(MenuResponseDto::new).toList();
        return new StoreWithMenusResponseDto(from, menuResponseDtoList);
    }

}
