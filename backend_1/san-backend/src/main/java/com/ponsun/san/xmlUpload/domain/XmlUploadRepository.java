package com.ponsun.san.xmlUpload.domain;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface XmlUploadRepository extends JpaRepository<XmlUpload, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE XmlUpload u SET u.filePath = :filePath, u.processingTime = :processingTime WHERE u.id = :id")
    void updateFilePathAndProcessingTimeById(@Param("id") Integer id,
                                             @Param("filePath") String filePath,
                                             @Param("processingTime") Double processingTime);

    boolean existsByFileNameAndUidAndPathId(String fileName, Integer uid, Integer pathId);
}

