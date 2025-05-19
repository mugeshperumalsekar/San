package com.ponsun.san.bulkTaskAssign.data;

import lombok.Data;

@Data
public class BulkTaskAssignData {
    private  Integer assignTo;
    private Integer assignBy;
    private String searchName;
    private Integer searchId;
    private Integer isTaskAssigned;
    private Integer matchingScore;
    private Integer euid;
    private Integer uid;

    public BulkTaskAssignData(Integer assignTo, Integer assignBy,String searchName ,Integer searchId, Integer isTaskAssigned,Integer euid, Integer uid,Integer matchingScore) {
        this.assignTo = assignTo;
        this.assignBy = assignBy;
        this.searchName = searchName;
        this.searchId = searchId;
        this.isTaskAssigned = isTaskAssigned;
        this.matchingScore =matchingScore;
        this.euid = euid;
        this.uid = uid;
    }

    public static BulkTaskAssignData newInstance(Integer assignTo, Integer assignBy, String searchName ,Integer searchId,Integer isTaskAssigned, Integer euid, Integer uid,Integer matchingScore) {
        return new BulkTaskAssignData(assignTo, assignBy,searchName,searchId,isTaskAssigned, matchingScore,euid, uid);
    }
}
