package com.ponsun.san.algorithm.testingservice;

import com.ponsun.san.oneSidePara.arabicSan.data.ArabicSanData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.MultiParaSearchData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.UiReciveSingleRecordDto;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.Functions;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.Preprocess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ChunkProcessingService {
    private final ScoringService scoringService;

//    public ChunkProcessingService(ScoringService scoringService) {
//        this.scoringService = scoringService;
//    }

    public List<UiReciveSingleRecordDto> processChunk(List<ArabicSanData> chunk, MultiParaSearchData multiParaSearchData) {
        return chunk.stream()
                .map(batchData -> processSingleRecord(batchData, multiParaSearchData))
                .collect(Collectors.toList());
    }

    private UiReciveSingleRecordDto processSingleRecord(ArabicSanData batchData, MultiParaSearchData multiParaSearchData) {
        try {
            // Prepare filters and tokens
            String filter1 = Preprocess.datafilter_Init(multiParaSearchData.getName());
            List<String> token1 = Functions.tokenizeString_Array(filter1);
            String filter2 = Preprocess.datafilter_Init(batchData.getName());
            List<String> token2 = Functions.tokenizeString_Array(filter2);
            Map<String, List<String>> out = Functions.similarfinding(token1, token2);

            // Calculate scores using the ScoringService
            double[] scores = scoringService.calculateScores(multiParaSearchData, batchData, filter1, token1, filter2, token2, out);

            return new UiReciveSingleRecordDto(batchData.getPersonId(), scores, batchData.getName(), batchData.getFullDate(), batchData.getIdValue(), batchData.getCountryName());
        } catch (Exception e) {
            // Log exception and return empty DTO
            System.err.println("Error processing data for name: " + batchData.getName() + ": " + e);
            return null;
        }
    }
}
