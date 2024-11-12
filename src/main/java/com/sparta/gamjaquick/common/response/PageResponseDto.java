package com.sparta.gamjaquick.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponseDto<T> {

    private final List<T> items; // 현재 페이지의 데이터 리스트
    private final int pageNumber; // 현재 페이지 번호
    private final int pageSize; // 현재 페이지 사이즈
    private final long totalElements; // 전체 데이터 개수
    private final int totalPages; // 전체 페이지 수
    private final boolean first; // 첫번째 페이지 여부
    private final boolean last; // 마지막 페이지 여부

    @Builder
    private PageResponseDto(List<T> items, int pageNumber, int pageSize, long totalElements, int totalPages, boolean first, boolean last) {
        this.items = items;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.first = first;
        this.last = last;
    }

    public static <T> PageResponseDto<T> of(Page<T> page) {
        return PageResponseDto.<T>builder()
                .items(page.getContent())
                .pageNumber(page.getNumber() + 1) // 페이지 번호를 1부터 시작하기 위함
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }

}
