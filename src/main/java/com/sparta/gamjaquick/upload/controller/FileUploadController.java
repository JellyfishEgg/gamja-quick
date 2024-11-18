package com.sparta.gamjaquick.upload.controller;

import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.upload.dto.response.UploadResponseDto;
import com.sparta.gamjaquick.upload.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/uploads")
@Tag(name = "File Upload", description = "파일 업로드 API")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("")
    @Operation(summary = "S3 사진 업로드", description = "S3에 사진을 업로드 할 때 사용하는 API")
    public ApiResponseDto<?> uploadFiles(@RequestParam(value = "files", required = false) List<MultipartFile> files) {
        List<UploadResponseDto> result = fileUploadService.uploadFiles(files);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

}
