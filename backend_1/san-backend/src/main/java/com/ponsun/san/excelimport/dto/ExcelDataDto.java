package com.ponsun.san.excelimport.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ExcelDataDto {
    private String name;
    private double score;
    private String type;
    private String country;

    public ExcelDataDto (String name,double score,String type,String country) {
        this.name = name;
        this.score=score;
        this.type=type;
        this.country=country;
    }
    public static ExcelDataDto newInstance (String name,double score,String type,String country){
        return new ExcelDataDto(name,score,type,country);
    }
}