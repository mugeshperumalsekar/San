package com.ponsun.san.xmlReadFile.ukSan.urlInterFace.service;


import com.ponsun.san.xmlReadFile.commonDto.Data.ArabicSanData;
import com.ponsun.san.xmlReadFile.commonDto.Data.MultiParaSearchData;
import com.ponsun.san.xmlReadFile.commonDto.Data.UiSingleParaResponse;

import java.util.List;

public interface ArabicSanService {


    List<UiSingleParaResponse> searchSanctionedPersons(MultiParaSearchData multiParaSearchData,Integer fileType);

    List<ArabicSanData> getArabicSanctions();
}
