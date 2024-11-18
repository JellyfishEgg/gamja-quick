package com.sparta.gamjaquick.upload.provider;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploader {

    String uploadFile(MultipartFile file);

}
