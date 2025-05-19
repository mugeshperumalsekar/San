package com.ponsun.san.xmlReadFile.unscfile.urlinterface.data;

import lombok.Data;

@Data
public class IndividualInfoDto {

    private String name;
    private Integer personId;
    private String addressCountry;
    private String birthCountry;
    private String nationality;
    private String birthPlaceCountry;
    private String birthDate;
    private String idValues;

    public IndividualInfoDto(String name, Integer personId, String addressCountry, String birthCountry, String nationality, String birthPlaceCountry, String birthDate, String idValues){
        this.name = name;
        this.personId = personId;
        this.addressCountry = addressCountry;
        this.birthCountry = birthCountry;
        this.nationality = nationality;
        this.birthPlaceCountry = birthPlaceCountry;
        this.birthDate = birthDate;
//        this.extraDate = extraDate;
        this.idValues = idValues;
    }
    public static IndividualInfoDto newInstance(String name, Integer personId, String addressCountry, String birthCountry, String nationality, String birthPlaceCountry, String birthDate, String idValues){
        return new IndividualInfoDto(name, personId, addressCountry, birthCountry, nationality, birthPlaceCountry, birthDate, idValues);
    }
}