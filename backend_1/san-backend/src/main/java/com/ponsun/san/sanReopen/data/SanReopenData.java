package com.ponsun.san.sanReopen.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SanReopenData {
    private Integer closedCaseId;
    private Integer caseId;
    private Integer hitId;
    private String closedCreatedAt;
    private String closedUpdatedAt;
    private String remark;  // Added closed remark
    private String searchName;
    private String criminalName;
    private Integer uid;
    private Integer statusId;
    private Integer searchId;
    private Integer criminalId;
    private Integer passingLevelId;
    private String reopenedCreatedAt;
    private String reopenedUpdatedAt;
    private Integer reopenCount;
    private String reopenIds;
    private String reopenstatusId;
    private String reopen_passingLevelId;
    private String reopenUids; // ✅ FIX: Added reopenUids field
    private String reopenRemarks;
    private String avgReprocessingTimeSeconds;
}
