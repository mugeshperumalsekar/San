package com.ponsun.san.xmlReadFile.ukSan.urlInterFace.Data;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UiSingleParaResponse {
    private Integer persion_id;
    private double[] onsideMultiPara;
    private String name;
    private String dob;
    private String id;
    private String country;

    public UiSingleParaResponse (final Integer persion_id, final double[] onsideMultiPara, final String name, final String dob, final String id, final String country) {
        this.persion_id=persion_id;
        this.onsideMultiPara=onsideMultiPara;
        this.name=name;
        this.dob=dob;
        this.id=id;
        this.country=country;
    }

    public static UiSingleParaResponse newInstance (final Integer persion_id, final double[] onsideMultiPara, final String name, final String dob, final String id, final String country) {
        return new UiSingleParaResponse(persion_id, onsideMultiPara, name, dob, id, country);
    }
}