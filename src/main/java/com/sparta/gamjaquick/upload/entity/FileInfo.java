package com.sparta.gamjaquick.upload.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_file_info")
@Entity
public class FileInfo extends AuditingFields {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Comment("파일 정보 고유 ID")
    private UUID id;

    @Column(nullable = false)
    @Comment("저장된 파일 이름")
    private String fileName;

    @Column(nullable = false)
    @Comment("원본 파일 이름")
    private String originalFileName;

    @Column(nullable = false)
    @Comment("파일 저장 경로")
    private String filePath;

    @Column(nullable = false)
    @Comment("파일 형식")
    private String fileType;

    @Column(nullable = false)
    @Comment("파일 크기 (바이트)")
    private Long fileSize;

}
