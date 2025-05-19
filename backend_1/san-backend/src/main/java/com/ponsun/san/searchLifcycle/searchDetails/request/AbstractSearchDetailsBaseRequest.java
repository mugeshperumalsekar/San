package com.ponsun.san.searchLifcycle.searchDetails.request;

import lombok.Data;

@Data
public abstract class AbstractSearchDetailsBaseRequest {
    private Integer id;
    private String name;
    private Double matching_score;
    private Integer listId;
    private Integer typeId;
    private Integer stateId;
    private Integer countryId;
    private String identity;
    private Long levelId;
    private Integer uid;
    private Boolean valid;
    private Integer kycId;
}
