package com.ponsun.san.oneSidePara.oneSideMultiPara.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UiReciveSingleRecordDto {
    private Integer persion_id;
    private double[] onsideMultiPara;
    private String name;
    private String dob;
    private String id;
    private String country;

    public UiReciveSingleRecordDto(Integer persion_id,double[] onsideMultiPara, String name, String dob, String id, String country) {
        this.persion_id=persion_id;
        this.onsideMultiPara = onsideMultiPara;
        this.name = name;
        this.dob = dob;
        this.id = id;
        this.country = country;
    }

    public static UiReciveSingleRecordDto newInstance(Integer persion_id,double[] onsideMultiPara, String name, String dob, String id, String country){
        return new UiReciveSingleRecordDto( persion_id, onsideMultiPara,  name,  dob,  id,  country);
    }

}
