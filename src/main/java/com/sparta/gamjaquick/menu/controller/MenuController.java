package com.sparta.gamjaquick.menu.controller;

import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.global.swagger.ApiErrorCodeExample;
import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.menu.dto.request.ContentRequestDto;
import com.sparta.gamjaquick.menu.dto.request.MenuRequestDto;
import com.sparta.gamjaquick.menu.dto.response.ContentResponseDto;
import com.sparta.gamjaquick.menu.dto.response.MenuDeleteReponseDto;
import com.sparta.gamjaquick.menu.dto.response.MenuResponseDto;
import com.sparta.gamjaquick.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stores/{storeId}")
@RequiredArgsConstructor
@Tag(name = "Menu", description = "메뉴 관련 API")
public class MenuController {

    //전체적으로 응답 형식이 정해지면 응답은 수정예정입니당.

    private final MenuService menuService;

    //메뉴 등록
    @PostMapping("/menus")
    @ApiErrorCodeExample(ErrorCode.STORE_NOT_FOUND)
    @Operation(summary = "메뉴 등록", description = "메뉴를 등록 할 때 사용하는 API")
    @Parameter(name = "storeId", description = "가게 ID", example = "c0a80018-9323-1fa9-8193-239fc7e00000")
    public ApiResponseDto<MenuResponseDto> createMenu(@PathVariable(name = "storeId") UUID storeId,
                                                      @RequestBody @Valid MenuRequestDto menuRequestDto) {
        MenuResponseDto result = menuService.createMenu(storeId, menuRequestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    //메뉴 수정
    @Operation(summary = "메뉴 수정", description = "메뉴를 수정 할 때 사용하는 API")
    @PutMapping("/menus/{menuId}")
    @Parameter(name = "menuId", description = "메뉴 ID", example = "7f000001-9328-1505-8193-28353dc60000")
    @ApiErrorCodeExample(ErrorCode.MENU_NOT_FOUND)
    public ApiResponseDto<MenuResponseDto> updateMenu(
                           @PathVariable(name = "menuId") UUID menuId,
                           @RequestBody @Valid MenuRequestDto menuRequestDto) {
        MenuResponseDto result = menuService.updateMenu(menuId, menuRequestDto);

        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    //메뉴 단건 조회
    @Operation(summary = "메뉴 단건 조회", description = "하나의 메뉴를 조회 할 때 사용하는 API")
    @GetMapping("/menus/{menuId}")
    @Parameters(
            {@Parameter(name = "storeId", description = "가게 ID", example = "c0a80018-9323-1fa9-8193-239fc7e00000"),
            @Parameter(name = "menuId", description = "메뉴 ID", example = "7f000001-9328-1505-8193-28353dc60000")}
    )
    @ApiErrorCodeExample(ErrorCode.MENU_NOT_FOUND)
    public ApiResponseDto<MenuResponseDto> getMenu(@PathVariable(name = "storeId") UUID storeId,
                                                   @PathVariable(name = "menuId") UUID menuId) {
        MenuResponseDto result = menuService.getMenu(menuId);

        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    //메뉴 전체 조회
    @Operation(summary = "메뉴 전체 조회 ", description = "가게별 전체 메뉴를 조회 하거나 검색 할 때 사용하는 API")
    @GetMapping("/menus")
    @Parameters(
            {@Parameter(name = "storeId", description = "가게 ID", example = "c0a80018-9323-1fa9-8193-239fc7e00000"),
            @Parameter(name = "keyword", description = "검색어", example = "감자"),
            @Parameter(name = "page", description = "페이지", example = "1"),
            @Parameter(name = "size", description = "한 페이지에 보일 메뉴 수", example = "10")}
    )
    @ApiErrorCodeExample(ErrorCode.MENU_NOT_FOUND)
    public ApiResponseDto<Page<MenuResponseDto>> getMenusByStore(@PathVariable(name = "storeId") UUID storeId,
                                                                 @RequestParam(name = "keyword", required = false) String keyword,
                                                                 @RequestParam(name="page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<MenuResponseDto> responseList = menuService.getMenusByStore(storeId, keyword, page, size);
        return ApiResponseDto.success(MessageType.RETRIEVE, responseList);
    }

    //메뉴 단건 삭제
    @Operation(summary = "메뉴 단건 삭제", description = "하나의 메뉴를 삭제 할 때 사용하는 API")
    @DeleteMapping("/menus/{menuId}")
    @Parameters(
            {@Parameter(name = "storeId", description = "가게 ID", example = "c0a80018-9323-1fa9-8193-239fc7e00000"),
            @Parameter(name = "menuId", description = "메뉴 ID", example = "7f000001-9328-1505-8193-28353dc60000")}
    )
    @ApiErrorCodeExample(ErrorCode.MENU_NOT_FOUND)
    public ApiResponseDto<MenuDeleteReponseDto> deleteMenu(@PathVariable(name = "storeId") UUID storeId,
                                                           @PathVariable(name = "menuId") UUID menuId) {
        return ApiResponseDto.success(MessageType.DELETE, menuService.deleteMenu(menuId));
    }

    //메뉴 전체 삭제
    @Operation(summary = "메뉴 전체 삭제", description = "가게별 메뉴를 전체 삭제 할 때 사용하는 API")
    @DeleteMapping("/menus")
    @Parameter(name = "storeId", description = "가게 ID", example = "c0a80018-9323-1fa9-8193-239fc7e00000")
    @ApiErrorCodeExample(ErrorCode.MENU_NOT_FOUND)
    public ApiResponseDto<List<MenuDeleteReponseDto>> deleteMenusByStore(@PathVariable(name = "storeId") UUID storeId) {
        return ApiResponseDto.success(MessageType.DELETE, menuService.deleteMenusByStore(storeId));
    }

    // 메뉴 설명 자동 생성
    @Operation(summary = "메뉴 설명 자동 생성", description = "메뉴의 설명을 자동 생성 할 때 사용하는 API")
    @PostMapping("/menus/generate-description")
    @Parameter(name = "storeId", description = "가게 ID", example = "c0a80018-9323-1fa9-8193-239fc7e00000")
    @ApiErrorCodeExample(ErrorCode.STORE_NOT_FOUND)
    public ApiResponseDto<?> generateMenuDescription(@PathVariable(name = "storeId") String storeId,
                                                     @RequestBody @Valid ContentRequestDto requestDto) {
        ContentResponseDto result = menuService.generateMenuDescription(storeId, requestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

}
