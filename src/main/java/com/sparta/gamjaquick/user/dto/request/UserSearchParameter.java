package com.sparta.gamjaquick.user.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class UserSearchParameter {
    private String username;
    private String email;
    private String phoneNumber;
    private String sortBy = "createdAt";
    private Sort.Direction sortDirection = Sort.Direction.ASC;

}