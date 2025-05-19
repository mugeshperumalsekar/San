package com.ponsun.san.fileType.services;

import com.ponsun.san.fileType.request.CreateFileTypeRequest;
import com.ponsun.san.fileType.request.UpdateFileTypeRequest;
import com.ponsun.san.infrastructure.utils.Response;

public interface FileTypeWritePlatformService {
    Response createFileType(CreateFileTypeRequest createFileTypeRequest);
    Response updateFileType(Integer id, UpdateFileTypeRequest updateFileTypeRequest);
    Response deactive(Integer id,Integer euid);

    Response blockFileType(Integer id);
    Response unblockFileType(Integer id);
}
