package com.ponsun.san.SanSortInsert.data;

import lombok.Data;

@Data
public class SanSortInsertData {
    private Integer ids;
    private String heading;
    private String val;

    public SanSortInsertData(Integer ids, String heading, String val) {
        this.ids = ids;
        this.heading = heading;
        this.val = val;
    }
    public static SanSortInsertData newInstance(Integer ids, String heading, String val){
        return new SanSortInsertData(ids,heading,val);
    }
}
