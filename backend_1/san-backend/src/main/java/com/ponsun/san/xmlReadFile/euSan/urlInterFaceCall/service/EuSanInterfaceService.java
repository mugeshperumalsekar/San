package com.ponsun.san.xmlReadFile.euSan.urlInterFaceCall.service;

import com.ponsun.san.xmlReadFile.commonDto.Data.MultiParaSearchData;
import com.ponsun.san.xmlReadFile.commonDto.Data.UiSingleParaResponse;

import java.util.List;

public interface EuSanInterfaceService {
    List<UiSingleParaResponse> getAlgorithmInterface(MultiParaSearchData searchDTO, Integer fileType);
}
