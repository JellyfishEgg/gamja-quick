package com.sparta.gamjaquick.category.repository;

import com.sparta.gamjaquick.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
