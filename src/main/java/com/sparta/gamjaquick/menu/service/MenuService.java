package com.sparta.gamjaquick.menu.service;

import com.sparta.gamjaquick.menu.dto.request.MenuRequestDto;
import com.sparta.gamjaquick.menu.dto.response.MenuResponseDto;
import com.sparta.gamjaquick.menu.entity.Menu;
import com.sparta.gamjaquick.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        List<Menu> menuList = null; //menuRepository.findAllByStore(storeId);

        if(menuList.isEmpty()){
            throw new RuntimeException("삐용삐용!!!");
        }

        return menuList.stream().map(menu -> new MenuResponseDto(menu)).collect(Collectors.toList());

    }

    @Transactional
    public void deleteMenusByStore(UUID storeId) {
        List<Menu> menuList = null;//menuRepository.findAllByStore(storeId);
        if(menuList.isEmpty()){
            throw new RuntimeException("삐용삐용!!!");
        }

        menuList.forEach(menu -> menu.deleteMenu());

    }


    @Transactional
    public MenuResponseDto updateMenu(UUID menuId, MenuRequestDto menuRequestDto) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new RuntimeException("삐용삐용!!!")
        );

        menu.updateByMenuDto(menuRequestDto);
        return new MenuResponseDto(menu);

    }
}
