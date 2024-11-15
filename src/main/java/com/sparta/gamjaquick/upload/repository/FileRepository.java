package com.sparta.gamjaquick.upload.repository;

import com.sparta.gamjaquick.upload.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepository extends JpaRepository<FileInfo, UUID> {
}
