package com.ponsun.san.xmlReadFile.euSan.urlInterFaceCall.api;

import com.ponsun.san.xmlReadFile.commonDto.Data.MultiParaSearchData;
import com.ponsun.san.xmlReadFile.commonDto.Data.UiSingleParaResponse;
import com.ponsun.san.xmlReadFile.euSan.urlInterFaceCall.service.EuSanUrlSanInterfaceService;
import com.ponsun.san.xmlReadFile.euSan.urlInterFaceCall.service.EuSanInterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/eufileurlinterface")
public class EuSanUrlInterfaceController {

    private final EuSanUrlSanInterfaceService euSanUrlInterfaceService;
    private final EuSanInterfaceService euSanInterfaceService;

    @PostMapping("/SanSearchAlgorithmInterface")
    public List<UiSingleParaResponse> getAlgorithmInterface(@RequestBody MultiParaSearchData searchDTO, @RequestParam Integer fileType){
        return this.euSanInterfaceService.getAlgorithmInterface(searchDTO, fileType);
    }
}

