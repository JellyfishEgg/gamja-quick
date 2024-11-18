package com.sparta.gamjaquick.common.request;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SearchParameter {

    private int page = 1; // 페이지 번호
    private int limit = 10; // 한 페이지에 보여지는 데이터 수
    private String keyword; // 검색 키워드
    private String orderBy = "createdAt"; // 정렬에 사용되는 필드
    private Sort.Direction sort = Sort.Direction.DESC; // 정렬 순서 (ASC | DESC)

    // 페이지 객체 만들 때 사용
    public Pageable getPageable() {
        return PageRequest.of(page - 1, getLimit());
    }

    // 필수 기능 중 '서치 기능에는 10건, 30건, 50건 기준으로 페이지에 노출 될수 있습니다.' 충족
    public int getLimit() {
        return (limit == 10 || limit == 30 || limit == 50) ? limit : 10;
    }

}
