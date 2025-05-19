package com.ponsun.san.xmlUpload.service;

import com.ponsun.san.xmlUpload.data.XmlUploadData;

import java.util.List;

public interface XmlUploadWritePlatformService {
    List<XmlUploadData> fetchAllUploads();
}
