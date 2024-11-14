package com.sparta.gamjaquick.user.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class UserSearchParameter {
    private String username;
    private String email;
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    private String sortBy = "createdAt"; // 기본 정렬 기준
}