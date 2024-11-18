package com.sparta.gamjaquick.upload.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UploadResponseDto {

    private String imageName;
    private String imageUrl;

    public static UploadResponseDto from(String imageName, String imageUrl) {
        return UploadResponseDto.builder()
                .imageName(imageName)
                .imageUrl(imageUrl)
                .build();
    }

}
