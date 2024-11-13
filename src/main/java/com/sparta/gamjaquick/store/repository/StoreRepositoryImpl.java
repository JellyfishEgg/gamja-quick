package com.sparta.gamjaquick.store.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.gamjaquick.common.request.StoreSearchParameter;
import com.sparta.gamjaquick.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

import static com.sparta.gamjaquick.store.entity.QStore.store;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Store> searchStores(StoreSearchParameter searchParameter) {

        // 페이징 처리
        int skip = (searchParameter.getPage() - 1) * searchParameter.getLimit();

        List<Store> results = queryFactory.selectFrom(store)
                .where(getSearchParameterBuilder(searchParameter)
                        .and(store.isDeleted.eq(false)))
                .orderBy(searchParameter.getSort() == Sort.Direction.ASC
                        ? store.createdAt.asc()
                        : store.createdAt.desc())
                .offset(skip)
                .limit(searchParameter.getLimit())
                .fetch();

        // 전체 개수 쿼리
        long totalCount = getTotalCount(searchParameter);

        return new PageImpl<>(results, searchParameter.getPageable(), totalCount);
    }

    // 가게 목록 전체 개수 조회
    private long getTotalCount(StoreSearchParameter searchParameter) {
        return queryFactory.select(Wildcard.count)
                .from(store)
                .where(getSearchParameterBuilder(searchParameter)
                        .and(store.isDeleted.eq(false)))
                .fetch().getFirst();
    }

    // 검색 키워드 조건 처리
    private BooleanBuilder getSearchParameterBuilder(StoreSearchParameter searchParameter) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(searchParameter.getCategoryId())) {
            builder.and(store.category.id.eq(UUID.fromString(searchParameter.getCategoryId())));
        }

        if (StringUtils.hasText(searchParameter.getKeyword())) {
            builder.and(store.name.containsIgnoreCase(searchParameter.getKeyword()));
        }

        return builder;
    }
}
