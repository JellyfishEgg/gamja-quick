package com.sparta.gamjaquick.menu.service;

import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.global.error.exception.BusinessException;
import com.sparta.gamjaquick.menu.dto.response.MenuDeleteReponseDto;
import com.sparta.gamjaquick.menu.dto.request.MenuRequestDto;
import com.sparta.gamjaquick.menu.dto.response.MenuResponseDto;
import com.sparta.gamjaquick.menu.entity.Menu;
import com.sparta.gamjaquick.menu.repository.MenuRepository;
import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final AuditorAware<String> auditorAware;
    private final StoreService storeService;

    public MenuResponseDto createMenu(UUID storeId, MenuRequestDto menuRequestDto) {
        //으아악 여기 나중에 고치기 !!!!!
        Store store = storeService.findById(String.valueOf(storeId));
        Menu menu=menuRepository.save(new Menu(store, menuRequestDto));
        return new MenuResponseDto(menu);
    }

    @Transactional
    public MenuDeleteReponseDto deleteMenu(UUID menuId) {
        Menu menu = checkMenu(menuId);

        return menu.deleteMenu(auditorAware.getCurrentAuditor().orElse(""));
    }



    public MenuResponseDto getMenu(UUID menuId) {
        Menu menu = checkMenu(menuId);

        return new MenuResponseDto(menu);
    }

    //페이징 해야대
    public Page<MenuResponseDto> getMenusByStore(UUID storeId, String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Menu> menuList = null;

        if(keyword==null ||keyword.isEmpty()){
            menuList = menuRepository.findAllByStoreIdAndIsDeletedFalse(storeId,pageable);
        }else{
            menuList = menuRepository.findAllByStoreIdAndNameContainingAndIsDeletedFalse(storeId, keyword, pageable);
        }

        if (menuList.isEmpty()){
            throw new BusinessException(ErrorCode.MENU_NOT_FOUND);
        }

        return menuList.map(menu -> new MenuResponseDto(menu));

    }

    @Transactional
    public List<MenuDeleteReponseDto> deleteMenusByStore(UUID storeId) {
        List<Menu> menuList = menuRepository.findAllByStoreIdAndIsDeletedFalse(storeId);
        if(menuList.isEmpty()){
            throw new BusinessException(ErrorCode.MENU_NOT_FOUND);
        }

        return menuList.stream()
                .map(menu -> menu.deleteMenu(auditorAware.getCurrentAuditor().orElse("")))
                .toList();
    }


    @Transactional
    public MenuResponseDto updateMenu(UUID menuId, MenuRequestDto menuRequestDto) {
        Menu menu = checkMenu(menuId);
        menu.updateByMenuDto(menuRequestDto);
        return new MenuResponseDto(menu);
    }

    public Menu checkMenu(UUID menuId){
        return menuRepository.findByIdAndIsDeletedFalse(menuId).orElseThrow(
                () -> new BusinessException(ErrorCode.MENU_NOT_FOUND)
        );
    }








}
