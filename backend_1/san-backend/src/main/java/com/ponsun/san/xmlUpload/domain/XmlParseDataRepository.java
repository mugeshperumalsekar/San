package com.ponsun.san.xmlUpload.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface XmlParseDataRepository extends JpaRepository<XmlParseData, Long> {
    List<XmlParseData> findByUploadIdAndRecordId(Long uploadId, String recordId);
}
