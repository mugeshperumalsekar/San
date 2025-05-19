package com.ponsun.san.xmlReadFile.commonDto.service;

import com.ponsun.san.xmlReadFile.commonDto.Data.ArabicSanData;
import com.ponsun.san.xmlReadFile.commonDto.Data.MultiParaSearchData;
import com.ponsun.san.xmlReadFile.commonDto.Data.UiSingleParaResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AlgorithmSearchService {

    List<UiSingleParaResponse> calculateAlgorithmSearch(MultiParaSearchData searchData, List<ArabicSanData> countryData,Integer fileType) throws ExecutionException, InterruptedException;
    List<UiSingleParaResponse> externalApiCall(MultiParaSearchData multiParaSearchData, List<ArabicSanData> filtered);
}
