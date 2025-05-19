package com.ponsun.san.oneSidePara.oneSideMultiPara.data;

import com.ponsun.san.oneSidePara.arabicSan.data.ArabicSanData;
import lombok.Data;

import java.util.List;

@Data
public class UiSanctionSearchRequest {
    private MultiParaSearchData multiParaSearchData;
    private List<ArabicSanData> sanList;
}

