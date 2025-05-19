package com.ponsun.san.xmlUpload.domain;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface XmlUploadLogRepository extends JpaRepository<XmlUploadLog, Integer> {
    @Transactional
    int deleteByUploadedAtBefore(LocalDateTime date);
}
