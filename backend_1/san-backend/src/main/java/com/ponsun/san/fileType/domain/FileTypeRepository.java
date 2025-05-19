package com.ponsun.san.fileType.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FileTypeRepository extends JpaRepository<FileType,Integer> {
   Optional<FileType> findById(Integer id);
}
