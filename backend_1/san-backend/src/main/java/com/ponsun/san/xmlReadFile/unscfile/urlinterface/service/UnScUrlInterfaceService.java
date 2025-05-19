package com.ponsun.san.xmlReadFile.unscfile.urlinterface.service;

import com.ponsun.san.xmlReadFile.commonDto.Data.MultiParaSearchData;
import com.ponsun.san.xmlReadFile.commonDto.Data.UiSingleParaResponse;
import com.ponsun.san.xmlReadFile.unscfile.urlinterface.data.IndividualInfoDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UnScUrlInterfaceService {
    List<UiSingleParaResponse> searchSanctionedPersons(MultiParaSearchData multiParaSearchData,Integer fileType) throws ExecutionException, InterruptedException;
    List<IndividualInfoDto> getIndividualInfo();
}
