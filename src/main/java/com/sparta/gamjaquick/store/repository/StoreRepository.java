package com.sparta.gamjaquick.store.repository;

import com.sparta.gamjaquick.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
}
