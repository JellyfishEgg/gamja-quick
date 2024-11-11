package com.sparta.gamjaquick.category.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.gamjaquick.category.entity.Category;
import com.sparta.gamjaquick.common.request.SearchParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sparta.gamjaquick.category.entity.QCategory.category;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Category> searchCategories(SearchParameter searchParameter) {

        // 페이징 처리
        int skip = (searchParameter.getPage() - 1) * searchParameter.getLimit();

        List<Category> results = queryFactory.selectFrom(category)
                .where(getSearchParameterBuilder(searchParameter))
                .orderBy(searchParameter.getSort() == Sort.Direction.ASC
                        ? category.createdAt.asc()
                        : category.createdAt.desc())
                .offset(skip)
                .limit(searchParameter.getLimit())
                .fetch();

        // 전체 개수 쿼리
        long totalCount = getTotalCount(searchParameter);

        return new PageImpl<>(results, searchParameter.getPageable(), totalCount);
    }

    // 카테고리 목록 전체 개수 조회
    private long getTotalCount(SearchParameter searchParameter) {
        return queryFactory.select(Wildcard.count)
                .from(category)
                .where(getSearchParameterBuilder(searchParameter))
                .fetch().getFirst();
    }

    // 검색 키워드 조건 처리
    private BooleanBuilder getSearchParameterBuilder(SearchParameter searchParameter) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(searchParameter.getKeyword())) {
            builder.and(category.name.containsIgnoreCase(searchParameter.getKeyword()));
        }
        return builder;
    }

}
