package com.sparta.gamjaquick.menu.repository;

import com.sparta.gamjaquick.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface MenuRepository extends JpaRepository<Menu, UUID> {
    //store id 로 가게안의 모든 메뉴를 조회 하는 기능
    List<Menu> findAllByStoreIdAndIsDeletedFalse(UUID storeId);

    Page<Menu> findAllByStoreIdAndIsDeletedFalse(UUID storeId, Pageable pageable);

    Page<Menu> findAllByStoreIdAndNameContainingAndIsDeletedFalse(UUID storeId, String keyword, Pageable pageable);

    Optional<Menu> findByIdAndIsDeletedFalse(UUID menuId);
}
