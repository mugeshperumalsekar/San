package com.ponsun.san.searchLifcycle.searchDetails.data;

import lombok.Data;

@Data
public class SearchDetailsData {
    private Integer id;
    private String name;
    private Double matching_score;
    private Integer listId;
    private Integer typeId;
    private Integer stateId;
    private Integer countryId;
    private String identify;
    private Long levelId;
    private Integer uid;
    private Boolean valid;
    private Integer kycId;
    public SearchDetailsData(final Integer id,final String name,final Double matching_score,final Integer listId,final Integer typeId,final Integer stateId,final Integer countryId,final String identify,final Long levelId,final Integer uid,final Boolean valid,final  Integer kycId){
        this.id = id;
        this.name = name;
        this.matching_score = matching_score;
        this.listId = listId;
        this.typeId = typeId;
        this.stateId = stateId;
        this.countryId = countryId;
        this.identify = identify;
        this.levelId = levelId;
        this.uid = uid;
        this.valid = valid;
        this.kycId = kycId;
    }
    public static SearchDetailsData newInstance(final Integer id,final String name,final Double matching_score,final Integer listId,final Integer typeId,final  Integer stateId,final Integer countryId,final String identify,final Long levelId,final Integer uid,final Boolean valid,final Integer kycId){
        return new SearchDetailsData(id,name,matching_score,listId,typeId,stateId,countryId,identify,levelId,uid,valid,kycId);
    }
}
