package com.ponsun.san.xmlReadFile.unscfile.urlinterface.data;


import lombok.Data;

import java.util.List;

@Data
public class UiSanctionSearchRequest {
    private MultiParaSearchData multiParaSearchData;
    private List<ArabicSanData> arabicSanData;
}
