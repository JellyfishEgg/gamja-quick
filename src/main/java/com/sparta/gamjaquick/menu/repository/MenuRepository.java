package com.sparta.gamjaquick.menu.repository;

import com.sparta.gamjaquick.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


public interface MenuRepository extends JpaRepository<Menu, UUID> {
    //List<Menu> findAllByStore(UUID storeId);
}
