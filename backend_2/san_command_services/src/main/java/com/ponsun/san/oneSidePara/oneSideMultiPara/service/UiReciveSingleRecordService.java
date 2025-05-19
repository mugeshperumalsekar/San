package com.ponsun.san.oneSidePara.oneSideMultiPara.service;

import com.ponsun.san.oneSidePara.arabicSan.data.ArabicSanData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.MultiParaSearchData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.UiReciveSingleRecordDto;

import java.util.List;

public interface UiReciveSingleRecordService {
    List<UiReciveSingleRecordDto> getUiSingleParaAlgorithmRecords(MultiParaSearchData multiParaSearchData);
    List<UiReciveSingleRecordDto> getSanctionsSearchAlgorithmRecords(MultiParaSearchData multiParaSearchData,List<ArabicSanData> sanList);



//    List<UiReciveSingleRecordDto> getOnBoardingAlgorithmRecords(MultiParaSearchData multiParaSearchData);

}
