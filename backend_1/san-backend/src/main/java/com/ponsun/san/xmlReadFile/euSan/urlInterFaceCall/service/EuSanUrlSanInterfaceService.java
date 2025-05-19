package com.ponsun.san.xmlReadFile.euSan.urlInterFaceCall.service;

import com.ponsun.san.xmlReadFile.commonDto.Data.ArabicSanData;
import com.ponsun.san.xmlReadFile.commonDto.Data.MultiParaSearchData;
import com.ponsun.san.xmlReadFile.commonDto.Data.UiSingleParaResponse;
import com.ponsun.san.xmlReadFile.commonDto.service.AlgorithmSearchService;
import com.ponsun.san.xmlReadFile.euSan.personCountry.service.PersonCountryReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EuSanUrlSanInterfaceService implements EuSanInterfaceService {
    @Autowired
    private RestTemplate restTemplate;
    private final PersonCountryReadService personCountryReadService;
    private final AlgorithmSearchService algorithmSearchService;

    @Override
    public List<UiSingleParaResponse> getAlgorithmInterface(MultiParaSearchData searchData, Integer fileType) {
        try {
            String name = searchData.getName();
            log.info("Searching for name: {}", name);
            List<ArabicSanData> sanDatasList = this.personCountryReadService.getAllPersonDetails();
            List<UiSingleParaResponse> recordDTOList = this.algorithmSearchService.calculateAlgorithmSearch(searchData, sanDatasList,fileType);
            return recordDTOList;
        } catch (DataAccessException e) {
            System.err.println("Error getTotalRecordsCount: " + e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
