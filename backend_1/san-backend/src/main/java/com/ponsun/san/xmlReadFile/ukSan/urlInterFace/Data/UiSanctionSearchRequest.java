package com.ponsun.san.xmlReadFile.ukSan.urlInterFace.Data;

import lombok.Data;

import java.util.List;

@Data
public class UiSanctionSearchRequest {
    private MultiParaSearchData multiParaSearchData;
    private List<ArabicSanData> sanList;

    public UiSanctionSearchRequest(MultiParaSearchData multiParaSearchData, List<ArabicSanData> sanList) {
        this.multiParaSearchData = multiParaSearchData;
        this.sanList = sanList;
    }
}
