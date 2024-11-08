package com.sparta.gamjaquick.menu.service;

import com.sparta.gamjaquick.menu.dto.request.MenuRequestDto;
import com.sparta.gamjaquick.menu.dto.response.MenuResponseDto;
import com.sparta.gamjaquick.menu.entity.Menu;
import com.sparta.gamjaquick.menu.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuResponseDto createMenu(UUID storeId, MenuRequestDto menuRequestDto) {
        Menu menu=menuRepository.save(new Menu(storeId, menuRequestDto));
        return new MenuResponseDto(menu);
    }

    @Transactional
    public void deleteMenu(UUID menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new RuntimeException("삐용삐용!!!")
        );
        menu.deleteMenu();

    }

    public MenuResponseDto getMenu(UUID menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new RuntimeException("삐용삐용!!!")
        );

        return new MenuResponseDto(menu);
    }

    public List<MenuResponseDto> getMenusByStore(UUID storeId) {
        List<Menu> menuList = menuRepository.findAllByStore(storeId);

        if(menuList.isEmpty()){
            throw new RuntimeException("삐용삐용!!!");
        }

        return null;

    }

    @Transactional
    public void deleteMenusByStore(UUID storeId) {
        List<Menu> menuList = menuRepository.findAllByStore(storeId);
        if(menuList.isEmpty()){
            throw new RuntimeException("삐용삐용!!!");
        }

        menuList.stream().forEach(menu -> menu.deleteMenu());

    }


//    @Transactional
//    public MenuResponseDto updateMenu(UUID menuId, MenuRequestDto menuRequestDto) {
//        Menu menu = menuRepository.findById(menuId).orElseThrow(
//                () -> new RuntimeException("삐용삐용!!!")
//        );
//
//        menu.updateByMenuDto(menuRequestDto);
//        return null;
//
//    }
}
