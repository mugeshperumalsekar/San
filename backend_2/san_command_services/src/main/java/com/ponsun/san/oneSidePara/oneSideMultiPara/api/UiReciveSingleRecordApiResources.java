package com.ponsun.san.oneSidePara.oneSideMultiPara.api;

import com.ponsun.san.oneSidePara.arabicSan.data.ArabicSanData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.MultiParaSearchData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.UiReciveSingleRecordDto;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.UiSanctionSearchRequest;
import com.ponsun.san.oneSidePara.oneSideMultiPara.service.FileService;
import com.ponsun.san.oneSidePara.oneSideMultiPara.service.UiReciveSingleRecord;
import com.ponsun.san.oneSidePara.oneSideMultiPara.service.UiReciveSingleRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/UiReciveSingleRecordApiResources")
@Tag(name = "UiReciveSingleRecordApiResources")
public class UiReciveSingleRecordApiResources {

    private final UiReciveSingleRecordService uiReciveSingleRecordService;
    private final UiReciveSingleRecord uiReciveSingleRecord;
    private final FileService fileService;

    @GetMapping("/calculateScorestestingService")
    public double[] calculateScores(@RequestParam Integer entityType,@RequestParam String name1, @RequestParam String name2, @RequestParam String id1,@RequestParam String id2,@RequestParam String country1,@RequestParam String country2,@RequestParam String dob1,@RequestParam String dob2) throws Exception {
        return uiReciveSingleRecord.calculateScorestestingService(entityType,name1,  name2,  id1,  id2,  country1,  country2, dob1, dob2);
    }
    @GetMapping("/UiReciveSingleRecord")
    List<UiReciveSingleRecordDto> getUiSingleParaAlgorithmRecords(MultiParaSearchData multiParaSearchData){
        return this.uiReciveSingleRecordService.getUiSingleParaAlgorithmRecords(multiParaSearchData);
    }
    @PostMapping("/SanctionsSearch")
    public List<UiReciveSingleRecordDto> getSanctionsSearchAlgorithmRecords(@RequestBody UiSanctionSearchRequest request) {
        return this.uiReciveSingleRecordService.getSanctionsSearchAlgorithmRecords(
                request.getMultiParaSearchData(),
                request.getSanList()
        );
    }
    @PostMapping("/fileSave")
    public double[] fileSave(@RequestParam(required = false) Integer entityType,
                             @RequestParam(required = false, defaultValue = "") String name1,
                             @RequestParam(required = false, defaultValue = "") String name2,
                             @RequestParam(required = false, defaultValue = "") String id1,
                             @RequestParam(required = false, defaultValue = "") String id2,
                             @RequestParam(required = false, defaultValue = "") String country1,
                             @RequestParam(required = false, defaultValue = "") String country2,
                             @RequestParam(required = false, defaultValue = "") String dob1,
                             @RequestParam(required = false, defaultValue = "") String dob2) throws Exception {
        if (entityType == null) {
            entityType = 1;
        }
        return uiReciveSingleRecord.calculateScorestestingService(entityType, name1, name2, id1, id2, country1, country2, dob1, dob2);
    }
}



