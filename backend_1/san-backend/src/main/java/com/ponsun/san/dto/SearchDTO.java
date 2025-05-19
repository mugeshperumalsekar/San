package com.ponsun.san.dto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SearchDTO {

    private String name;
    private Double matching_score;
    private Integer listID;
    private Integer partySubTypeID;
    private Integer countryId;
    private Integer isBulkSearch;
    private Integer entryType;
    private Integer uid;

    public SearchDTO(String name, double matching_score, Integer listID, Integer partySubTypeID, Integer countryId , Integer isBulkSearch , Integer entryType,Integer uid) {
        this.name = name;
        this.matching_score = matching_score;
        this.listID = listID;
        this.partySubTypeID = partySubTypeID;
        this.countryId = countryId;
        this.isBulkSearch = isBulkSearch;
        this.entryType = entryType;
        this.uid = uid;
    }
    public static SearchDTO newInstance(String name, double matching_score, Integer listID, Integer partySubTypeID, Integer countryId , Integer isBulkSearch , Integer entryType,Integer uid){
        return new SearchDTO(name, matching_score,listID,partySubTypeID,countryId,isBulkSearch,entryType,uid);
    }
}
