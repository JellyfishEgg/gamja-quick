package com.sparta.gamjaquick.store.repository;

import com.sparta.gamjaquick.common.request.StoreSearchParameter;
import com.sparta.gamjaquick.store.entity.Store;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface StoreRepositoryCustom {

    Page<Store> searchStores(StoreSearchParameter searchParameter);

    Optional<Store> findByIdWithRecentMenus(UUID storeId, int limit);

}
