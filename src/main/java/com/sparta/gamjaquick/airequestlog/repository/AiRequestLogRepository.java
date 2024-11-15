package com.sparta.gamjaquick.airequestlog.repository;

import com.sparta.gamjaquick.airequestlog.entity.AiRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AiRequestLogRepository extends JpaRepository<AiRequestLog, UUID> {
}
