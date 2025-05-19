package com.ponsun.san.xmlReadFile.commonDto.Data;

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
    private String sourceType;
    public UiSingleParaResponse (final Integer persion_id, final double[] onsideMultiPara, final String name, final String dob, final String id, final String country,final String sourceType) {
        this.persion_id=persion_id;
        this.onsideMultiPara=onsideMultiPara;
        this.name=name;
        this.dob=dob;
        this.id=id;
        this.country=country;
        this.sourceType = sourceType;
    }
    public static UiSingleParaResponse newInstance (final Integer persion_id, final double[] onsideMultiPara, final String name, final String dob, final String id, final String country,final String sourceType) {
        return new UiSingleParaResponse(persion_id, onsideMultiPara, name, dob, id, country,sourceType);
    }
}