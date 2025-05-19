package com.ponsun.san.oneSidePara.oneSideMultiPara.data;
import lombok.Data;

@Data
public class MultiParaSearchData {
    private String name;
    private String dob;
    private String id;
    private String country;
    private Integer entityType;
    private Double score;

    public MultiParaSearchData(String name, String dob, String id, String country, Double score,Integer entityType) {
        this.name = name;
        this.dob = dob;
        this.id = id;
        this.country = country;
        this.score = score;
        this.entityType=entityType;
    }
    public static MultiParaSearchData newInstance(String name, String dob, String id, String country, Double score,Integer entityType){
        return new MultiParaSearchData( name,dob,id,country,score,entityType);
    }

}
