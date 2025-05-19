package com.ponsun.san.algorithm.api;

import com.ponsun.san.algorithm.testingservice.UICalculationService;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.MultiParaSearchData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.UiReciveSingleRecordDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/ReciveRecordApiResources")
@Tag(name = "ReciveRecordApiResources")
public class ReciveRecordApiResources {

    private final UICalculationService uiCalculationService;

    @PostMapping("/ReciveRecord")
    public CompletableFuture<List<UiReciveSingleRecordDto>> calculateScore(@RequestBody MultiParaSearchData multiParaSearchData) {
        return uiCalculationService.uicalculateScoreSinglePara(multiParaSearchData);
    }
//    List<UiReciveSingleRecordDto> getReciveRecordAlgorithmRecords(MultiParaSearchData multiParaSearchData){
//        return this.uiCalculationService.uicalculateScoreSinglePara(multiParaSearchData);
//    }

}



