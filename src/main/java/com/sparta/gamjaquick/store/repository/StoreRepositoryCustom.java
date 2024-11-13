package com.sparta.gamjaquick.store.repository;

import com.sparta.gamjaquick.common.request.StoreSearchParameter;
import com.sparta.gamjaquick.store.entity.Store;
import org.springframework.data.domain.Page;

public interface StoreRepositoryCustom {

    Page<Store> searchStores(StoreSearchParameter searchParameter);

}
