package com.ponsun.san.uiTest.dto;

import lombok.Data;

@Data

public class UiReciveRecord {
    private double onesidematching;
    private double twosidematching;
    private double Jaro;
    private double tokenWeight;
    private double onsideSinglePara;
    private double onsideMultiPara[];

    public UiReciveRecord(double onesidematching, double twosidematching,double Jaro, double onsideSinglePara,double onsideMultiPara[]) {
        this.onesidematching = onesidematching;
        this.twosidematching = twosidematching;
        this.Jaro   = Jaro;
        this.onsideSinglePara = onsideSinglePara;
        this.onsideMultiPara    = onsideMultiPara;
    }
    public static UiReciveRecord newInstance(double onesidematching, double twosidematching,double Jaro,double onsideSinglePara,double onsideMultiPara[]) {
        return new UiReciveRecord(onesidematching,twosidematching,Jaro,onsideSinglePara,onsideMultiPara);
    }

    public UiReciveRecord(){}
}
