package com.ponsun.san.xmlReadFile.commonDto.api;

import com.ponsun.san.xmlReadFile.commonDto.Data.MultiParaSearchData;
import com.ponsun.san.xmlReadFile.commonDto.Data.UiSingleParaResponse;
import com.ponsun.san.xmlReadFile.euSan.urlInterFaceCall.service.EuSanInterfaceService;
import com.ponsun.san.xmlReadFile.ukSan.urlInterFace.service.ArabicSanService;
import com.ponsun.san.xmlReadFile.unscfile.urlinterface.service.UnScUrlInterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/AlgorithmSearchApi")
public class AlgorithmSearchApi {

    private final ArabicSanService arabicSanService;
    private final UnScUrlInterfaceService unScUrlInterfaceService;
    private final EuSanInterfaceService euSanInterfaceService;

    @PostMapping("/fetch-SearchSanctionedPersons")
    public ResponseEntity<List<UiSingleParaResponse>> getUisearchSanctionedPersons(@RequestBody MultiParaSearchData input, @RequestParam Integer fileType) {
        try {
            List<UiSingleParaResponse> result = new ArrayList<>();

            if (fileType == 2) {//eu
                result = euSanInterfaceService.getAlgorithmInterface(input, fileType);
                System.out.println("result" + result);
            }
            if (fileType == 3) {//uk
                result = arabicSanService.searchSanctionedPersons(input, fileType);
            }
            if (fileType == 4) {//un
                result = unScUrlInterfaceService.searchSanctionedPersons(input, fileType);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Failed to fetch single para record", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    //To combine all the three files (eu, uk, un) into a single compressed file.
//    @PostMapping("/search-sanctioned-persons")
//    public ResponseEntity<List<UiSingleParaResponse>> searchSanctionedPersonsByFileType(@RequestBody MultiParaSearchData searchData, @RequestParam Integer fileType) {
//        try {
//            List<UiSingleParaResponse> searchResults = new ArrayList<>();
//
//            switch (fileType) {
//                case 2: // EU
//                    searchResults = euSanInterfaceService.getAlgorithmInterface(searchData, fileType);
//                    System.out.println("EU Search Results: " + searchResults);
//                    break;
//                case 3: // UK (Arabic)
//                    searchResults = arabicSanService.searchSanctionedPersons(searchData, fileType);
//                    System.out.println("UK Search Results: " + searchResults);
//                    break;
//                case 4: // UN
//                    searchResults = unScUrlInterfaceService.searchSanctionedPersons(searchData, fileType);
//                    System.out.println("UN Search Results: " + searchResults);
//                    break;
//                default:
//                    log.error("Invalid fileType provided: " + fileType);
//                    return ResponseEntity.badRequest().build();
//            }
//            return ResponseEntity.ok(searchResults);
//        } catch (Exception e) {
//            log.error("Error while searching sanctioned persons", e);
//            return ResponseEntity.internalServerErro  r().build();
//        }
//    }

    //To combine all the three files (eu, uk, un) into a single compressed file.
    @PostMapping("/search-sanctioned-persons")
    public ResponseEntity<?> searchSanctionedPersonsByFileType(@RequestBody MultiParaSearchData searchData, @RequestParam Integer fileType) {
        try {
            List<UiSingleParaResponse> searchResults = new ArrayList<>();

            switch (fileType) {
                case 2: // EU
                    searchResults = euSanInterfaceService.getAlgorithmInterface(searchData, fileType);
                    log.info("EU Search Results: {}", searchResults);
                    break;
                case 3: // UK (Arabic)
                    searchResults = arabicSanService.searchSanctionedPersons(searchData, fileType);
                    log.info("UK Search Results: {}", searchResults);
                    break;
                case 4: // UN
                    searchResults = unScUrlInterfaceService.searchSanctionedPersons(searchData, fileType);
                    log.info("UN Search Results: {}", searchResults);
                    break;
                default:
                    log.error("Invalid fileType provided: {}", fileType);
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Invalid file type.Please provide a Valid file type.");
                    return ResponseEntity.badRequest().body(error);
            }
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            log.error("RuntimeException:{}", e.getMessage());
            if (e.getMessage().contains("No new CSV files found")) {
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            Map<String, String> error = new HashMap<>();
            error.put("error", "Runtime error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Unexpected error while searching sanctioned persons", e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Unexpected error occured");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

//    @PostMapping("/Allsearch-sanctioned-persons")
//    public ResponseEntity<?> searchAllSanctionedPersons(@RequestBody MultiParaSearchData searchData) {
//        try {
//            List<UiSingleParaResponse> finalResults = new ArrayList<>();
//
//            // Call EU service
//            List<UiSingleParaResponse> euResults = euSanInterfaceService.getAlgorithmInterface(searchData, 2);
//            euResults.forEach(r -> r.setSourceType("EU"));
//            log.info("EU Results: {}", euResults);
//            finalResults.addAll(euResults);
//
//            // Call UK Arabic service
//            List<UiSingleParaResponse> ukResults = arabicSanService.searchSanctionedPersons(searchData, 3);
//            ukResults.forEach(r -> r.setSourceType("UK Arabic"));
//            log.info("UK Arabic Results: {}", ukResults);
//            finalResults.addAll(ukResults);
//
//            // Call UN service
//            List<UiSingleParaResponse> unResults = unScUrlInterfaceService.searchSanctionedPersons(searchData, 4);
//            unResults.forEach(r -> r.setSourceType("UN"));
//            log.info("UN Results: {}", unResults);
//            finalResults.addAll(unResults);
//
//            return ResponseEntity.ok(finalResults);
//
//        } catch (RuntimeException e) {
//            log.error("RuntimeException: {}", e.getMessage());
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Runtime error: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        } catch (Exception e) {
//            log.error("Unexpected error while searching sanctioned persons", e);
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Unexpected error occurred");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }

    @PostMapping("/Allsearch-sanctioned-persons")
    public ResponseEntity<?> searchAllSanctionedPersons(@RequestBody MultiParaSearchData searchData) {
        List<UiSingleParaResponse> finalResults = new ArrayList<>();

        try {
            // EU Service
            try {
                List<UiSingleParaResponse> euResults = euSanInterfaceService.getAlgorithmInterface(searchData, 2);
                euResults.forEach(r -> r.setSourceType("EU"));
                log.info("EU Results: {}", euResults);
                finalResults.addAll(euResults);
            } catch (Exception e) {
                log.error("EU service failed: {}", e.getMessage());
            }

            // UK Arabic Service
            try {
                List<UiSingleParaResponse> ukResults = arabicSanService.searchSanctionedPersons(searchData, 3);
                ukResults.forEach(r -> r.setSourceType("UK Arabic"));
                log.info("UK Arabic Results: {}", ukResults);
                finalResults.addAll(ukResults);
            } catch (Exception e) {
                log.error("UK Arabic service failed: {}", e.getMessage());
            }

            // UN Service
            try {
                List<UiSingleParaResponse> unResults = unScUrlInterfaceService.searchSanctionedPersons(searchData, 4);
                unResults.forEach(r -> r.setSourceType("UN"));
                log.info("UN Results: {}", unResults);
                finalResults.addAll(unResults);
            } catch (Exception e) {
                log.error("UN service failed: {}", e.getMessage());
            }

            return ResponseEntity.ok(finalResults);

        } catch (Exception e) {
            log.error("Unexpected error while searching sanctioned persons", e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
