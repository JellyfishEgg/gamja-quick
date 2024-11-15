package com.sparta.gamjaquick.upload.service;

import com.sparta.gamjaquick.upload.dto.response.UploadResponseDto;
import com.sparta.gamjaquick.upload.provider.FileUploader;
import com.sparta.gamjaquick.upload.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final FileRepository fileRepository;
    private final FileUploader fileUploader;

    public List<UploadResponseDto> uploadFiles(List<MultipartFile> files) {
        return files.stream()
                .filter(file -> !file.isEmpty())
                .map(file -> {
                    String imageUrl = fileUploader.uploadFile(file);
                    return UploadResponseDto.from(file.getOriginalFilename(), imageUrl);
                }).toList();
    }

}
