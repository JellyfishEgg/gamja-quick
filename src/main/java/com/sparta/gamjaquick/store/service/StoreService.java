package com.sparta.gamjaquick.store.service;

import com.sparta.gamjaquick.category.entity.Category;
import com.sparta.gamjaquick.category.service.CategoryService;
import com.sparta.gamjaquick.common.request.StoreSearchParameter;
import com.sparta.gamjaquick.common.response.PageResponseDto;
import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.global.error.exception.BusinessException;
import com.sparta.gamjaquick.menu.entity.Menu;
import com.sparta.gamjaquick.store.dto.request.StoreApprovalRequestDto;
import com.sparta.gamjaquick.store.dto.request.StoreCreateRequestDto;
import com.sparta.gamjaquick.store.dto.request.StoreUpdateRequestDto;
import com.sparta.gamjaquick.store.dto.response.StoreCreateResponseDto;
import com.sparta.gamjaquick.store.dto.response.StoreResponseDto;
import com.sparta.gamjaquick.store.dto.response.StoreWithMenusResponseDto;
import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.store.repository.StoreRepository;
import com.sparta.gamjaquick.user.entity.RoleType;
import com.sparta.gamjaquick.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryService categoryService;

    // 가게 등록 신청 (가게 주인) / 관리자의 경우 바로 등록
    public StoreCreateResponseDto registerStore(StoreCreateRequestDto requestDto, User user) {
        validateStoreNotExists(requestDto);
        Category findCategory = categoryService.findById(requestDto.getCategoryId());

        // 가게 생성
        Store createdStore = user.getRole().equals(RoleType.OWNER)
                ? storeRepository.save(Store.from(user, findCategory, requestDto))
                : storeRepository.save(Store.fromAdmin(user, findCategory, requestDto));

        return StoreCreateResponseDto.from(createdStore);
    }

    // 주소를 기반으로 중복 체크
    private void validateStoreNotExists(StoreCreateRequestDto requestDto) {
        storeRepository.findByAddressOrJibunAddress(requestDto.getRoadAddress(), requestDto.getJibunAddress())
                .ifPresent(store -> {
                    if (store.isAwaitingApproval()) {
                        throw new BusinessException(ErrorCode.STORE_APPROVAL_PENDING);
                    } else {
                        throw new BusinessException(ErrorCode.STORE_ALREADY_EXISTS);
                    }
                });
    }

    // 가게 승인 (관리자)
    public StoreResponseDto approveStore(String storeId, StoreApprovalRequestDto requestDto) {
        Store findStore = findById(storeId);
        if (!findStore.isAwaitingApproval()) {
            throw new BusinessException(ErrorCode.STORE_ALREADY_APPROVED);
        }

        findStore.processApproval(requestDto);
        return StoreResponseDto.from(findStore);
    }

    // 가게 목록 조회
    @Transactional(readOnly = true)
    public PageResponseDto<StoreResponseDto> getStoreList(StoreSearchParameter searchParameter) {
        Page<Store> stores = storeRepository.searchStores(searchParameter);
        Page<StoreResponseDto> result = stores.map(StoreResponseDto::from);
        return PageResponseDto.of(result);
    }

    // 특정 가게 조회
    @Transactional(readOnly = true)
    public StoreWithMenusResponseDto getStore(String storeId) {
        Store findStore = findByIdWithMenus(storeId, 5); // 최근 5개의 메뉴만 조회
        List<Menu> menuList = findStore.getMenuList();

        return StoreWithMenusResponseDto.from(findStore, menuList);
    }

    // 가게 수정
    public StoreResponseDto update(String storeId, @Valid StoreUpdateRequestDto requestDto, User user) {
        Store findStore = findById(storeId);
        Category findCategory = categoryService.findById(requestDto.getCategoryId());

        // 가게 주인인 경우 자신의 가게만 수정 가능
        if (user.getRole().equals(RoleType.OWNER) && !findStore.getUser().getId().equals(user.getId())) {
            throw new BusinessException(ErrorCode.STORE_UPDATE_UNAUTHORIZED);
        }
        findStore.update(requestDto, findCategory);

        return StoreResponseDto.from(findStore);
    }

    // 가게 삭제
    public StoreResponseDto delete(String storeId, User user) {
        Store findStore = findById(storeId);

        // 가게 주인인 경우 자신의 가게만 삭제 가능
        if (user.getRole().equals(RoleType.OWNER) && !findStore.getUser().getId().equals(user.getId())) {
            throw new BusinessException(ErrorCode.STORE_UPDATE_UNAUTHORIZED);
        }

        // 이미 삭제된 가게일 경우 예외 처리
        if (findStore.getIsDeleted()) {
            throw new BusinessException(ErrorCode.STORE_ALREADY_DELETED);
        }

        findStore.delete(user.getUsername()); // TODO: 로그인한 유저로 변경
        return StoreResponseDto.from(findStore);
    }

    public Store findById(String storeId) {
        return storeRepository.findByIdAndIsDeletedFalse(UUID.fromString(storeId)).orElseThrow(
                () -> new BusinessException(ErrorCode.STORE_NOT_FOUND)
        );
    }

    private Store findByIdWithMenus(String storeId, int limit) {
        return storeRepository.findByIdWithRecentMenus(UUID.fromString(storeId), limit).orElseThrow(
                () -> new BusinessException(ErrorCode.STORE_NOT_FOUND)
        );
    }
}
