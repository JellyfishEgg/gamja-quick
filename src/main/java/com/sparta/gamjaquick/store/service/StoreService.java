package com.sparta.gamjaquick.store.service;

import com.sparta.gamjaquick.category.entity.Category;
import com.sparta.gamjaquick.category.service.CategoryService;
import com.sparta.gamjaquick.common.request.StoreSearchParameter;
import com.sparta.gamjaquick.common.response.PageResponseDto;
import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.global.error.exception.BusinessException;
import com.sparta.gamjaquick.store.dto.response.StoreResponseDto;
import com.sparta.gamjaquick.store.dto.request.StoreApprovalRequestDto;
import com.sparta.gamjaquick.store.dto.request.StoreCreateRequestDto;
import com.sparta.gamjaquick.store.dto.response.StoreCreateResponseDto;
import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.store.repository.StoreRepository;
import com.sparta.gamjaquick.user.entity.User;
import com.sparta.gamjaquick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    // 가게 등록 신청 (가게 주인)
    public StoreCreateResponseDto registerStore(StoreCreateRequestDto requestDto) {
        validateStoreNotExists(requestDto);

        Category findCategory = categoryService.findById(requestDto.getCategoryId());
        // TODO: 시큐리시 적용 후 user 객체 주입
        User user = userRepository.findById(1L).get();
        Store createdStore = storeRepository.save(Store.from(user, findCategory, requestDto));

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

    public Store findById(String storeId) {
        return storeRepository.findById(UUID.fromString(storeId)).orElseThrow(
                () -> new BusinessException(ErrorCode.STORE_NOT_FOUND)
        );
    }

    // 가게 목록 조회
    @Transactional(readOnly = true)
    public PageResponseDto<StoreResponseDto> getStoreList(StoreSearchParameter searchParameter) {
        Page<Store> stores = storeRepository.searchStores(searchParameter);
        Page<StoreResponseDto> result = stores.map(StoreResponseDto::from);
        return PageResponseDto.of(result);
    }
}
