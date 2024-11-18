package com.sparta.gamjaquick.upload.controller;

import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.upload.dto.response.UploadResponseDto;
import com.sparta.gamjaquick.upload.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/uploads")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("")
    public ApiResponseDto<?> uploadFiles(@RequestParam(value = "files", required = false) List<MultipartFile> files) {
        List<UploadResponseDto> result = fileUploadService.uploadFiles(files);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

}
