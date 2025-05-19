package com.ponsun.san.fileType.services;

import com.ponsun.san.fileType.domain.FileType;
import java.util.List;
public interface FileTypeReadPlatformService {
    FileType fetchFileTypeById(Integer id);
    List<FileType> fetchAllFileType();
}
