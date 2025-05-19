package com.ponsun.san.xmlReadFile.unscfile.urlinterface.api;

import com.ponsun.san.xmlReadFile.commonDto.Data.MultiParaSearchData;
import com.ponsun.san.xmlReadFile.commonDto.Data.UiSingleParaResponse;
import com.ponsun.san.xmlReadFile.unscfile.urlinterface.service.UnScUrlInterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/un-sc-url-interface")
@RequiredArgsConstructor
@Slf4j
public class UnSCUrlInterfaceController {

    private final UnScUrlInterfaceService unScUrlInterfaceService;

    @PostMapping("/fetch-UnSearchSanctionedPersons")
    public ResponseEntity<List<UiSingleParaResponse>> getUisearchSanctionedPersons(@RequestBody MultiParaSearchData input) {
        try {
            List<UiSingleParaResponse> result = null; //unScUrlInterfaceService.searchSanctionedPersons(input);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Failed to fetch single para record", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
