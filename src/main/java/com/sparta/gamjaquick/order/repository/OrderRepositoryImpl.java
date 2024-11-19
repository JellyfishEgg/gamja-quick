package com.sparta.gamjaquick.order.repository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.gamjaquick.common.request.SearchParameter;
import com.sparta.gamjaquick.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

import static com.sparta.gamjaquick.order.entity.QOrder.order;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Order> searchOrders(SearchParameter searchParameter, Long userId, UUID storeId) {
        // 페이징 처리
        int skip = (searchParameter.getPage() - 1) * searchParameter.getLimit();

        BooleanBuilder whereCondition = new BooleanBuilder();
        whereCondition.and(order.isDeleted.eq(false));

        if (userId != null) {
            whereCondition.and(order.user.id.eq(userId));
        }

        if (storeId != null) {
            whereCondition.and(order.store.id.eq(storeId));
        }

        if (searchParameter.getKeyword() != null) {
            whereCondition.and(order.orderNumber.containsIgnoreCase(searchParameter.getKeyword()));
        }

        // 결과 조회
        List<Order> results = queryFactory.selectFrom(order)
                .where(whereCondition)
                .orderBy(searchParameter.getSort() == Sort.Direction.ASC
                        ? order.createdAt.asc()
                        : order.createdAt.desc())
                .offset(skip)
                .limit(searchParameter.getLimit())
                .fetch();

        // 전체 개수 조회
        long totalCount = queryFactory.select(Wildcard.count)
                .from(order)
                .where(whereCondition)
                .fetchOne();

        return new PageImpl<>(results, searchParameter.getPageable(), totalCount);
    }
}