package com.ponsun.san.ofac.Count.api;

import com.ponsun.san.EhcachePOC.Data.OFACData;
import com.ponsun.san.EhcachePOC.Service.OFACSearchService;
import com.ponsun.san.dto.RecordDTO;
import com.ponsun.san.dto.SearchDTO;
import com.ponsun.san.ofac.Count.data.SanctionDetailData;
import com.ponsun.san.ofac.Count.service.*;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/Count")
@Tag(name = "CountApiResources")
public class CountApiResources {
    private final CountReadService countReadService;
    private final RecordDetails recDet;
    private final SanctionDetailReadService sanctionDetailReadService;
    private final RecordDetails recordDetails;
    private final SanctionDetailReadServiceImpl sanctionDetailReadServiceimpl;
    private final OFACSearchService ofacSearchService;


//    @GetMapping("/{id}")
//    public List<Count> fetchAll(@RequestParam Integer id){
//        return this.countReadService.fetchAllCount(id);
//    }
    @GetMapping("/RecordsCount")
    public List<RecordDTO> getRecords(@RequestBody SearchDTO searchDTO){
        return this.countReadService.getRecordsCount(searchDTO);
    }


//    @GetMapping("/fetch")
//    public Map<String, SanctionDetailData> fetchSanctionData(
//            @RequestParam(required = false) Integer entityLogicalId,
//            @RequestParam(required = false) Integer groupId,
//            @RequestParam(required = false) Integer dataId) {
//        return sanctionDetailReadService.fetchSanctionData(entityLogicalId, groupId, dataId);
//    }

//    @GetMapping("/fetchEuSanctionData")
//    public SanctionDetailData fetchEuSanctionData(@RequestParam Integer entityLogicalId) {
//        return sanctionDetailReadService.fetchEuSanctionData(entityLogicalId);
//    }


    @GetMapping("/eu")
    public List<SanctionDetailData> fetchAllEUSanctionData() {
        return sanctionDetailReadService.fetchAllEUSanctionData();
    }
    @GetMapping("/uk")
    public List<SanctionDetailData>  fetchAllUKSanctionData() {
        return sanctionDetailReadService.fetchAllUKSanctionData();
    }
    @GetMapping("/un")
    public List<SanctionDetailData> fetchAllUnSanctionApiData() {
        return sanctionDetailReadService.fetchAllUnSanctionData();
    }

    @PostMapping("/set")
    public void setCacheData() {
        recordDetails.setCacheData();
    }

    @GetMapping("/ofacSearchdataAdd")
    public List<OFACData> fetchAll(){
        return this.ofacSearchService.fetchAllOFACData();
    }

    @DeleteMapping("/evict/allEuEnUk")
    public String evictAllCaches() {
        sanctionDetailReadServiceimpl.evictAllCaches();
        return "All caches have been evicted.";
    }

    @DeleteMapping("/evict/eu")
    public String evictAllEuSanctionData() {
        sanctionDetailReadServiceimpl.evictAllEuSanctionData();
        return "All EU Sanction Data has been evicted.";
    }

    @DeleteMapping("/evict/uk")
    public String evictAllUkSanctionData() {
        sanctionDetailReadServiceimpl.evictAllUkSanctionData();
        return "All UK Sanction Data has been evicted.";
    }

    @DeleteMapping("/evict/un")
    public String evictAllUnSanctionData() {
        sanctionDetailReadServiceimpl.evictAllUnSanctionData();
        return "All UN Sanction Data has been evicted.";
    }
    @DeleteMapping("/evict/allSet")
    public String evictAllCacheEntries() {
        log.info("Evicting all entries from the cache");
        recordDetails.evictAllCacheEntries();
        return "All cache entries have been evicted.";
    }

    @DeleteMapping("/ofacSearchdataDelete")
    public String deleteAll(){
        return this.ofacSearchService.clearAllCache();
    }



//    @GetMapping("/GetRecordsDet")
//    public List<Integer> getRecords(){
//        return this.recDet.getliststatus();
//    }
}
