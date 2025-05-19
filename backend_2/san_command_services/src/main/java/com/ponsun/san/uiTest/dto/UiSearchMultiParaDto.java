package com.ponsun.san.uiTest.dto;

import lombok.Data;

@Data
public class UiSearchMultiParaDto {
    private String id1;
    private String id2;
    private String country1;
    private String country2;
    private String dob1;
    private String dob2;
    private String name1;
    private String name2;
    private Double score;

    public UiSearchMultiParaDto(String id1, String id2, String country1, String country2, String dob1, String dob2, String name1, String name2,Double score) {
        this.id1 = id1;
        this.id2 = id2;
        this.country1 = country1;
        this.country2 = country2;
        this.dob1 = dob1;
        this.dob2 = dob2;
        this.name1 = name1;
        this.name2 = name2;
        this.score=score;
    }
    public static UiSearchMultiParaDto newInstance(String id1, String id2, String country1, String country2, String dob1, String dob2, String name1, String name2,Double score){
        return new UiSearchMultiParaDto( id1,  id2,  country1,  country2,  dob1,  dob2,  name1,  name2,score);
    }

}
