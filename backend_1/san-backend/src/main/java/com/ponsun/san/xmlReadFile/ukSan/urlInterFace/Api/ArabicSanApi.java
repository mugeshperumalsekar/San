package com.ponsun.san.xmlReadFile.ukSan.urlInterFace.Api;

import com.ponsun.san.xmlReadFile.euSan.urlInterFaceCall.service.EuSanInterfaceService;
import com.ponsun.san.xmlReadFile.ukSan.urlInterFace.Data.MultiParaSearchData;
import com.ponsun.san.xmlReadFile.ukSan.urlInterFace.Data.UiSingleParaResponse;
import com.ponsun.san.xmlReadFile.ukSan.urlInterFace.service.ArabicSanService;
import com.ponsun.san.xmlReadFile.unscfile.urlinterface.service.UnScUrlInterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/urlInterFace")
public class ArabicSanApi {

//    private final ArabicSanService arabicSanService;
//    private final UnScUrlInterfaceService unScUrlInterfaceService;
//    private final EuSanInterfaceService euSanInterfaceService;
//
//
//
//    @PostMapping("/fetch-SearchSanctionedPersons")
//    public ResponseEntity<List<UiSingleParaResponse>> getUisearchSanctionedPersons(@RequestBody MultiParaSearchData input, @RequestParam Integer fileType) {
//        try {
//            List<UiSingleParaResponse> result = new ArrayList<>();
//            if (fileType == 2) {//eu
//                 result = euSanInterfaceService.getAlgorithmInterface(input,fileType);
//                System.out.println("result"+result);
//            }
//            if(fileType == 3){//uk
//                result = arabicSanService.searchSanctionedPersons(input);
//            }
//            if(fileType == 4){//un
//                 result = unScUrlInterfaceService.searchSanctionedPersons(input);
//            }
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            log.error("Failed to fetch single para record", e);
//            return ResponseEntity.internalServerError().build();
//        }
//    }
}