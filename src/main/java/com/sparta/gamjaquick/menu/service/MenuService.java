package com.sparta.gamjaquick.menu.service;

import com.sparta.gamjaquick.airequestlog.service.AiRequestLogService;
import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.global.error.exception.BusinessException;
import com.sparta.gamjaquick.infra.ai.dto.AiApiResponseDto;
import com.sparta.gamjaquick.infra.ai.service.AiApiService;
import com.sparta.gamjaquick.menu.dto.request.ContentRequestDto;
import com.sparta.gamjaquick.menu.dto.request.MenuRequestDto;
import com.sparta.gamjaquick.menu.dto.response.ContentResponseDto;
import com.sparta.gamjaquick.menu.dto.response.MenuDeleteReponseDto;
import com.sparta.gamjaquick.menu.dto.response.MenuResponseDto;
import com.sparta.gamjaquick.menu.entity.Menu;
import com.sparta.gamjaquick.menu.repository.MenuRepository;
import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.store.service.StoreService;
import com.sparta.gamjaquick.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final AuditorAware<String> auditorAware;
    private final StoreService storeService;
    private final AiApiService aiApiService;
    private final AiRequestLogService aiRequestLogService;

    public MenuResponseDto createMenu(UUID storeId, MenuRequestDto menuRequestDto, User user) {

        checkUser(storeId,user);

        Store store = storeService.findById(String.valueOf(storeId));
        Menu menu=menuRepository.save(new Menu(store, menuRequestDto));
        return new MenuResponseDto(menu);
    }

    @Transactional
    public MenuDeleteReponseDto deleteMenu(UUID storeId,UUID menuId, User user) {

        checkUser(storeId,user);

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
    public List<MenuDeleteReponseDto> deleteMenusByStore(UUID storeId, User user) {

        checkUser(storeId,user);


        List<Menu> menuList = menuRepository.findAllByStoreIdAndIsDeletedFalse(storeId);
        if(menuList.isEmpty()){
            throw new BusinessException(ErrorCode.MENU_NOT_FOUND);
        }


        return menuList.stream()
                .map(menu -> menu.deleteMenu(auditorAware.getCurrentAuditor().orElse("")))
                .toList();
    }


    @Transactional
    public MenuResponseDto updateMenu(UUID storeId, UUID menuId, MenuRequestDto menuRequestDto, User user) {

        checkUser(storeId,user);

        Menu menu = checkMenu(menuId);
        menu.updateByMenuDto(menuRequestDto);
        return new MenuResponseDto(menu);
    }

    public Menu checkMenu(UUID menuId){
        return menuRepository.findByIdAndIsDeletedFalse(menuId).orElseThrow(
                () -> new BusinessException(ErrorCode.MENU_NOT_FOUND)
        );
    }

    @Transactional
    public ContentResponseDto generateMenuDescription(UUID storeId, ContentRequestDto requestDto, User user) {
        checkUser(storeId, user);
        AiApiResponseDto responseDto = aiApiService.generateText(requestDto.getContent());
        Store findStore = storeService.findById(String.valueOf(storeId));

        // 비동기로 AiRequestLog 저장
        CompletableFuture.runAsync(() -> {
            aiRequestLogService.createLog(findStore, requestDto.getContent(), responseDto);
        });

        return ContentResponseDto.from(responseDto.getFirstCandidateText());
    }


    public void checkUser(UUID storeId,User user) {
        //user에서 받은 권한이 들어갈 예정 ex) user.getRole().equals("OWNER")

        if(!user.getRole().equals("MASTER")){
            Store store = storeService.findById(String.valueOf(storeId));
            //System.out.println(store.getUser().getId() + "      " + user.getId());
            if(store.getUser().getId() != user.getId()){
                throw new BusinessException(ErrorCode.FORBIDDEN);
            }
        }



    }
}
