package com.ponsun.san.bulkTaskAssignView.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class BulkTaskAssignViewData {

    private Integer uid;
    private String searchName;
    private String userName;
    private Integer searchId;
    private Integer matchingScore;
    private LocalDateTime created_at;


    public BulkTaskAssignViewData (Integer uid, String searchName , String userName,Integer searchId,Integer matchingScore,LocalDateTime created_at ) {
        this.uid = uid;
        this.searchName = searchName;
        this.userName = userName;
        this.searchId = searchId;
        this.matchingScore = matchingScore;
        this.created_at = created_at;
    }

    public static BulkTaskAssignViewData newInstance (Integer uid,String searchName , String userName,Integer searchId,Integer matchingScore,LocalDateTime created_at ) {
        return new BulkTaskAssignViewData(uid , searchName , userName,searchId,matchingScore,created_at);
    }

}
