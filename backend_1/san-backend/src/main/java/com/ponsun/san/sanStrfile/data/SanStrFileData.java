package com.ponsun.san.sanStrfile.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanStrFileData {
    private Integer strFileCount;
    private String searchName;
    private Integer searchId;
    private Integer hitId;
    private Integer caseId;
    private Integer criminalId;
    private String criminalName;
    private Integer statusId;  // Add statusId
    private Integer passinglevelId;  // Add passinglevelId
}
