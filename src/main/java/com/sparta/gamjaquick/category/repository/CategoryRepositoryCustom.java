package com.sparta.gamjaquick.category.repository;

import com.sparta.gamjaquick.category.entity.Category;
import com.sparta.gamjaquick.common.request.SearchParameter;
import org.springframework.data.domain.Page;

public interface CategoryRepositoryCustom {

    Page<Category> searchCategories(SearchParameter searchParameter);
}
