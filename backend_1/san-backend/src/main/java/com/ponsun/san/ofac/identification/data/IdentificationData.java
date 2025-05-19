package com.ponsun.san.ofac.identification.data;
import lombok.Data;

@Data
public class IdentificationData  {
    private String TYPE;
    private String IDs;
    private String country;
    private String DateClarification;
    private String issue_Date;
    private String expire_Date;

    public IdentificationData(String TYPE,String IDs,String country,String DateClarification, String issue_Date,String expire_Date) {

        this.TYPE = TYPE;
        this.IDs = IDs;
        this.country = country;
        this.DateClarification = DateClarification;
        this.issue_Date = issue_Date;
        this.expire_Date = expire_Date;
   }

    public static IdentificationData newInstance( String TYPE, String IDs, String country,String DateClarification, String issue_Date,String expire_Date){
        return new IdentificationData(TYPE,IDs,country,DateClarification,issue_Date,expire_Date);
    }
}
