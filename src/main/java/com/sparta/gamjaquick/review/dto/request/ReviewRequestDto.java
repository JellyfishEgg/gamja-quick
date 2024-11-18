package com.sparta.gamjaquick.review.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {
    private String content;
    private int rating;
    private Boolean isHidden;
}