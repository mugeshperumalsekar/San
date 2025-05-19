package com.ponsun.san.bulkAssignMapping.data;

import lombok.Data;

@Data
    public class BulkAssignMappingData {
    private Integer id;
    private  Integer bulkAssignId;
    private Integer searchId;
    private Integer hitId;
    private Integer filetype;
    private String hitName;
    private Integer hit;
    private Integer euid;
    private Integer uid;

    public BulkAssignMappingData (Integer id, Integer bulkAssignId, Integer searchId,Integer hitId ,Integer filetype, String hitName,Integer hit, Integer euid, Integer uid) {
        this.id = id;
        this.bulkAssignId = bulkAssignId;
        this.searchId = searchId;
        this.hitId = hitId;
        this.filetype = filetype;
        this.hitName = hitName;
        this.hit = hit;
        this.euid = euid;
        this.uid = uid;

    }

    public static BulkAssignMappingData newInstance (Integer id, Integer bulkAssignId, Integer searchId,Integer hitId ,Integer filetype, String hitName,Integer hit, Integer euid, Integer uid) {
        return new BulkAssignMappingData(id , bulkAssignId, searchId,hitId,filetype, hitName , hit, euid, uid);
    }
}
