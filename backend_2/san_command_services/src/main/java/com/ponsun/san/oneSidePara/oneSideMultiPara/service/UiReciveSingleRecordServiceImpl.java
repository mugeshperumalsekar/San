package com.ponsun.san.oneSidePara.oneSideMultiPara.service;
import com.ponsun.san.oneSidePara.arabicSan.data.ArabicSanData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.MultiParaSearchData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.UiReciveSingleRecordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UiReciveSingleRecordServiceImpl implements UiReciveSingleRecordService {

    private final UiReciveSingleRecord uiReciveSingleRecordDto;

    @Override
    public List<UiReciveSingleRecordDto> getUiSingleParaAlgorithmRecords(MultiParaSearchData multiParaSearchData) {
        List<UiReciveSingleRecordDto> recordDTOList = uiReciveSingleRecordDto.uicalculateScoreSinglePara(multiParaSearchData);
//        List<UiReciveSingleRecordDto> recordDTOList = uiReciveSingleRecordDto.uicalculateScoreSingleParaTest(multiParaSearchData);
        return recordDTOList;
    }

    @Override
    public List<UiReciveSingleRecordDto> getSanctionsSearchAlgorithmRecords(MultiParaSearchData multiParaSearchData,List<ArabicSanData> sanList) {
        List<UiReciveSingleRecordDto> recordDTOList = uiReciveSingleRecordDto.calculateSanListScore(multiParaSearchData,sanList);
        return recordDTOList;
    }

//    @Override
//    public List<UiReciveSingleRecordDto> getOnBoardingAlgorithmRecords(MultiParaSearchData multiParaSearchData) {
//        List<UiReciveSingleRecordDto> recordDTOList = uiReciveSingleRecordDto.calculateOnBoardScore(multiParaSearchData);
//        return recordDTOList;
//    }


}
