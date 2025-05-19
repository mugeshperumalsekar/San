package com.ponsun.san.ofac.identification.rowmapper;
import com.ponsun.san.ofac.identification.data.IdentificationData;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
public class IdentificationRowMapper implements RowMapper<IdentificationData> {
    private final String schema;

    public IdentificationRowMapper() {
        final StringBuilder builder = new StringBuilder(200);

        builder.append(" FROM IDRegDocument a " +
                " LEFT OUTER JOIN `documentdate` b ON a.`PrimaryKey` = b.`FK_IDRegDocument` " +
                " LEFT JOIN dateperiod c ON c.`FK_DocumentDate` = b.`PrimaryKey` " +
                " LEFT JOIN `start` d ON d.`FK_DatePeriod` = c.`PrimaryKey` " +
                " LEFT JOIN `from` e ON e.`FK_Start` = d.`PrimaryKey` " +
                " LEFT JOIN idregdocdatetype g ON g.`ID` = b.`IDRegDocDateTypeID` " +
                " LEFT JOIN identity f ON f.`ID` = a.`IdentityID`");

//        builder.append(" FROM IDRegDocument a " +
//                "  LEFT OUTER JOIN  `documentdate` b ON a.`PrimaryKey`=b.`FK_IDRegDocument` " +
//                "  LEFT JOIN dateperiod c ON  c.`FK_DocumentDate`=b.`PrimaryKey` " +
//                "  LEFT JOIN `start` d ON d.`FK_DatePeriod`=c.`PrimaryKey`" +
//                "  LEFT JOIN `from` e ON e.`FK_Start`=d.`PrimaryKey` " +
//                "  ,identity f");
        this.schema = builder.toString();
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" (SELECT TEXT FROM idregdoctype x2 WHERE a.`IDRegDocTypeID` = x2.id ) AS TYPE, " +
                " (SELECT TEXT FROM country x1 WHERE a.`IssuedBy-CountryID` = x1.`ID`) AS country, " +
                " a.`IDRegistrationNo` AS IDs, " +
                " IFNULL((SELECT g.text FROM idregdocdatetype g WHERE g.`ID`=b.`IDRegDocDateTypeID`),'') AS DateClarification," +  // Added this line
                " MAX(CASE WHEN g.text = 'Issue Date' THEN DATE_FORMAT(STR_TO_DATE(CONCAT(e.`Year`, '-', e.`Month`, '-', e.`Day`), '%Y-%m-%d'), '%d %b %Y') END) AS issue_Date, " +
                " MAX(CASE WHEN g.text = 'Expiration Date' THEN DATE_FORMAT(DATE_ADD(STR_TO_DATE(CONCAT(e.`Year`, '-', e.`Month`, '-', e.`Day`), '%Y-%m-%d'), INTERVAL 10 YEAR), '%d %b %Y') END) AS expire_Date ");

//        builder.append(" (SELECT TEXT FROM idregdoctype x2 WHERE a.`IDRegDocTypeID`=x2.id ) AS TYPE," +
//                        "  (SELECT TEXT FROM country x1 WHERE  a.`IssuedBy-CountryID`=x1.`ID`) AS country," +
//                        "  " +
//                        "  a.`IDRegistrationNo` AS IDs," +
//                        "  IFNULL((SELECT g.text FROM idregdocdatetype g WHERE g.`ID`=b.`IDRegDocDateTypeID`),'') AS DateClarification," +
//                        "  IFNULL(DATE_FORMAT(CONCAT(e.`Year`,'-',e.`Month`,'-',e.`Day`),'%d %b %Y'),'') AS issue_Date");

//        builder.append("f.`Text` AS TYPE");
//        builder.append("b.`IDRegistrationNo` AS IDs");
//        builder.append("c.`Text` AS country");
//        builder.append("IdentityFromDate(e.`PrimaryKey`) AS issue_Date");
//        builder.append("IdentityToDate(e.`PrimaryKey`) AS expire_Date");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public IdentificationData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final String TYPE = rs.getString("TYPE");
        final String country = rs.getString("country");
        final String IDs = rs.getString("IDs");
        final String DateClarification = rs.getString("DateClarification");
        final String issue_Date = rs.getString("issue_Date");
        final String expire_Date = rs.getString("expire_Date");
        return new IdentificationData(TYPE, IDs, country, DateClarification, issue_Date,expire_Date);
    }
}