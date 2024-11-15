package com.sparta.gamjaquick.menu.controller;

import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.menu.dto.request.ContentRequestDto;
import com.sparta.gamjaquick.menu.dto.request.MenuRequestDto;
import com.sparta.gamjaquick.menu.dto.response.ContentResponseDto;
import com.sparta.gamjaquick.menu.dto.response.MenuDeleteReponseDto;
import com.sparta.gamjaquick.menu.dto.response.MenuResponseDto;
import com.sparta.gamjaquick.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ApiResponseDto<MenuResponseDto> createMenu(@PathVariable(name = "store_id") UUID storeId,
                                                      @RequestBody @Valid MenuRequestDto menuRequestDto) {
        MenuResponseDto result = menuService.createMenu(storeId, menuRequestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    //메뉴 수정
    @PutMapping("/menus/{menu_id}")
    public ApiResponseDto<MenuResponseDto> updateMenu(
                           @PathVariable(name = "menu_id") UUID menuId,
                           @RequestBody @Valid MenuRequestDto menuRequestDto) {
        MenuResponseDto result = menuService.updateMenu(menuId, menuRequestDto);

        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    //메뉴 단건 조회
    @GetMapping("/menus/{menu_id}")
    public ApiResponseDto<MenuResponseDto> getMenu(@PathVariable(name = "store_id") UUID storeId,
                                                   @PathVariable(name = "menu_id") UUID menuId) {
        MenuResponseDto result = menuService.getMenu(menuId);

        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    //메뉴 전체 조회
    @GetMapping("/menus")
    public ApiResponseDto<Page<MenuResponseDto>> getMenusByStore(@PathVariable(name = "store_id") UUID storeId,
                                                                 @RequestParam(name = "keyword", required = false) String keyword,
                                                                 @RequestParam("page") int page,
                                                                 @RequestParam("size") int size) {
        Page<MenuResponseDto> responseList = menuService.getMenusByStore(storeId, keyword, page, size);
        return ApiResponseDto.success(MessageType.RETRIEVE, responseList);
    }

    //메뉴 단건 삭제
    @DeleteMapping("/menus/{menu_id}")
    public ApiResponseDto<MenuDeleteReponseDto> deleteMenu(@PathVariable(name = "store_id") UUID storeId,
                                                           @PathVariable(name = "menu_id") UUID menuId) {
        return ApiResponseDto.success(MessageType.DELETE, menuService.deleteMenu(menuId));
    }

    //메뉴 전체 삭제
    @DeleteMapping("/menus")
    public ApiResponseDto<List<MenuDeleteReponseDto>> deleteMenusByStore(@PathVariable(name = "store_id") UUID storeId) {
        return ApiResponseDto.success(MessageType.DELETE, menuService.deleteMenusByStore(storeId));
    }

    // 메뉴 설명 자동 생성
    @PostMapping("/menus/generate-description")
    public ApiResponseDto<?> generateMenuDescription(@PathVariable(name = "store_id") String storeId,
                                                     @RequestBody @Valid ContentRequestDto requestDto) {
        ContentResponseDto result = menuService.generateMenuDescription(storeId, requestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

}
