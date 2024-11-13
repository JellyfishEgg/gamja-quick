package com.sparta.gamjaquick.store.service;

import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.global.error.exception.BusinessException;
import com.sparta.gamjaquick.store.dto.StoreResponseDto;
import com.sparta.gamjaquick.store.dto.request.CreateStoreRequestDto;
import com.sparta.gamjaquick.store.dto.response.CreateStoreResponseDto;
import com.sparta.gamjaquick.store.entity.Store;
import com.sparta.gamjaquick.store.repository.StoreRepository;
import com.sparta.gamjaquick.user.entity.User;
import com.sparta.gamjaquick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    // 가게 등록 신청 (가게 주인)
    public CreateStoreResponseDto registerStore(CreateStoreRequestDto requestDto) {
        validateStoreNotExists(requestDto);

        // TODO: 시큐리시 적용 후 user 객체 주입
        User user = userRepository.findById(1L).get();
        Store createdStore = storeRepository.save(Store.from(user, requestDto));

        return CreateStoreResponseDto.from(createdStore);
    }

    // 주소를 기반으로 중복 체크
    private void validateStoreNotExists(CreateStoreRequestDto requestDto) {
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
    public StoreResponseDto approveStore(String storeId) {
        return null;
    }
}
