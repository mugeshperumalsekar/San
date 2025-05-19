package com.ponsun.san.xmlReadFile.commonDto.Data;

import lombok.Data;

@Data
public class MultiParaSearchData {
    private String name;
    private String dob;
    private String id;
    private String country;
    private Integer entityType;
    private Integer score;
    private Long uid;

    public MultiParaSearchData (final String name, final String dob, final String id, final String country, final Integer entityType, final Integer score,final Long uid) {
        this.name=name;
        this.dob=dob;
        this.id=id;
        this.country=country;
        this.entityType=entityType;
        this.score=score;
        this.uid = uid;
    }
    public static MultiParaSearchData newInstance (final String name, final String dob, final String id, final String country, final Integer entityType, final Integer score,final Long uid) {
        return new MultiParaSearchData(name, dob, id, country, entityType, score,uid);
    }
}

