package com.sparta.gamjaquick.menu.repository;

import com.sparta.gamjaquick.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


public interface MenuRepository extends JpaRepository<Menu, UUID> {
    //store id 로 가게안의 모든 메뉴를 조회 하는 기능
    List<Menu> findAllByStoreId(UUID storeId);

}
