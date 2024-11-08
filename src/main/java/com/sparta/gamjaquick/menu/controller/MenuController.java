package com.sparta.gamjaquick.menu.controller;

import com.sparta.gamjaquick.menu.dto.request.MenuRequestDto;
import com.sparta.gamjaquick.menu.dto.response.MenuResponseDto;
import com.sparta.gamjaquick.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stores/{store_id}")
@RequiredArgsConstructor
public class MenuController {

    //전체적으로 응답 형식이 정해지면 응답은 수정예정입니당.

    private final MenuService menuService;

    //메뉴 등록
    @PostMapping("/menus")
    public void createMenu(@PathVariable UUID storeId, @RequestBody MenuRequestDto menuRequestDto) {
        MenuResponseDto response = menuService.createMenu(storeId, menuRequestDto);
    }

    //메뉴 수정
    @PutMapping("/menus/{menu_id}")
    public void updateMenu(
                           @PathVariable(name = "menu_id") UUID menuId,
                           @RequestBody MenuRequestDto menuRequestDto) {
        //MenuResponseDto response = menuService.updateMenu(menuId, menuRequestDto);

    }

    //메뉴 단건 조회
    @GetMapping("/menus/{menu_id}")
    public void getMenu(@PathVariable(name = "store_id") UUID storeId,
                        @PathVariable(name="menu_id") UUID menuId) {
        MenuResponseDto response = menuService.getMenu(menuId);

    }

    //메뉴 전체 조회
    @GetMapping("/menus")
    public void getMenusByStore(@PathVariable(name = "store_id") UUID storeId) {
        List<MenuResponseDto> responseList = menuService.getMenusByStore(storeId);
    }


    //메뉴 단건 삭제
    @DeleteMapping("/menus/{menu_id}")
    public void deleteMenu(@PathVariable(name = "store_id") UUID storeId,
                           @PathVariable(name = "menu_id") UUID menuId) {
        menuService.deleteMenu(menuId);
    }

    //메뉴 전체 삭제
    @DeleteMapping("/menus")
    public void deleteMenusByStore(@PathVariable(name = "store_id") UUID storeId) {
        menuService.deleteMenusByStore(storeId);
    }


}
