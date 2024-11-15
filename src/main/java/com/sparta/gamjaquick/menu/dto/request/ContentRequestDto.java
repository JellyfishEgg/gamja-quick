package com.sparta.gamjaquick.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ContentRequestDto {

    @NotBlank(message = "요청 내용을 입력해 주세요.")
    @Size(max = 100, message = "요청 내용은 100자 이하로 작성해 주세요.")
    private String content;

}
