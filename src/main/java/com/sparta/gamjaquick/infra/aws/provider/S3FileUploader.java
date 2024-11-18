package com.sparta.gamjaquick.infra.aws.provider;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.StringUtils;
import com.sparta.gamjaquick.global.error.ErrorCode;
import com.sparta.gamjaquick.global.error.exception.BusinessException;
import com.sparta.gamjaquick.infra.aws.config.S3Config;
import com.sparta.gamjaquick.upload.provider.FileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@Profile({"prod", "local"})
@RequiredArgsConstructor
public class S3FileUploader implements FileUploader {

    private final S3Config s3Config;
    private final AmazonS3 amazonS3;

    /**
     * AWS S3에 파일 업로드
     * @param file 업로드 파일
     * @return 업로드된 파일의 URL
     */
    @Override
    public String uploadFile(MultipartFile file) {
        if (file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
            throw new BusinessException(ErrorCode.FILE_NAME_MISSING);
        }
        String fileName = getFileName(file);
        return uploadFileToS3(fileName, file);
    }

    // S3에 파일 업로드
    private String uploadFileToS3(String fileName, MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            amazonS3.putObject(new PutObjectRequest(s3Config.getBucket(), fileName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.Private));

            // 업로드된 파일의 URL 반환
            return amazonS3.getUrl(s3Config.getBucket(), fileName).toString();

        } catch (IOException e) {
            log.error("S3 파일 업로드 중 에러 발생: {}", e.getMessage());
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    private String getFileName(MultipartFile file) {
        System.out.println("file.getContentType() = " + file.getContentType());
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image")) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_FILE_FORMAT);
        }
        String path = s3Config.getUploadPath();
        String sanitizedFileName = sanitizeFileName(file.getOriginalFilename());
        return path + generateUniqueFileName(sanitizedFileName);
    }

    private String sanitizeFileName(String originalFilename) {
        return StringUtils.replace(Objects.requireNonNull(originalFilename), " ", "_");
    }

    private String generateUniqueFileName(String sanitizedFileName) {
        String extension;
        String[] parts = sanitizedFileName.split("\\.");
        if (parts.length > 1) {
            extension = "." + parts[parts.length - 1];
        } else {
            extension = ".jpg";
        }
        return new Date().getTime() + "-" + UUID.randomUUID() + extension;
    }

}
