package com.ponsun.san.SanSortInsert.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@Slf4j
public class SanSortInsertWriteServiceImpl implements SanSortInsertWriteService {

   final private JdbcTemplate jdbcTemplate;
    @Override
    public void executeAddressInsertQuery() {
        String sql = " INSERT INTO san_sort_address" +
                " SELECT a.id,'Address' AS heading ,GROUP_CONCAT(DISTINCT j.`Text`) AS val" +
                " FROM `profile` a,`feature` b,`featureversion` c,`featureversionreference` d," +
                " `location` e,`locationpart` f,`locationpartvalue` g,`locparttype` h,`locationcountry` i,`country` j" +
                " WHERE a.`PrimaryKey`=b.`FK_Profile` AND b.`PrimaryKey`=c.`FK_Feature`" +
                " AND c.`ID`=d.`FeatureVersionID` AND d.`FK_Location`=e.`PrimaryKey`" +
                " AND f.`FK_Location`=e.`PrimaryKey`  AND g.`FK_LocationPart`=f.`PrimaryKey`" +
                " AND LocPartTypeID=h.`ID` AND i.`FK_Location`=e.`PrimaryKey` AND i.`CountryID`=j.`ID` GROUP BY a.`id`";

        jdbcTemplate.update(sql);
    }

    @Override
    public void executeCountryInsertQuery() {
        String sql = " INSERT INTO san_sort_country" +
                " SELECT d.`ID` AS ids,e.`Text` AS heading,GROUP_CONCAT(i.`Value`) AS val" +
                " FROM versiondetail a,featureversion b,feature c,`profile` d,featuretype e," +
                " `versionlocation` f,`location` g,`locationpart` h,`locationpartvalue` i" +
                " WHERE a.`FK_FeatureVersion`=b.`PrimaryKey` AND b.`FK_Feature`=c.`PrimaryKey` AND c.`FK_Profile`=d.`PrimaryKey`" +
                " AND e.`ID`=c.`FeatureTypeID` AND h.`FK_Location`=g.`PrimaryKey` AND i.`FK_LocationPart`=h.`PrimaryKey`" +
                " AND f.`FK_FeatureVersion`=b.`PrimaryKey` AND f.`LocationID`=g.`ID`" +
                " AND e.id IN (10,11) GROUP BY d.`ID`,2";

        jdbcTemplate.update(sql);
    }
    @Override
    public void executeIdentificatioInsertQuery() {
        String sql = " INSERT INTO san_sort_identification" +
                " SELECT f.`FixedRef` AS ids,'Identifications' AS heading ,GROUP_CONCAT(DISTINCT (SELECT TEXT FROM country x1 WHERE  a.`IssuedBy-CountryID`=x1.`ID`)) AS val" +
                " FROM IDRegDocument a" +
                " LEFT OUTER JOIN  `documentdate` b ON a.`PrimaryKey`=b.`FK_IDRegDocument`" +
                " LEFT JOIN dateperiod c ON  c.`FK_DocumentDate`=b.`PrimaryKey`" +
                " LEFT JOIN `start` d ON d.`FK_DatePeriod`=c.`PrimaryKey`" +
                " LEFT JOIN `from` e ON e.`FK_Start`=d.`PrimaryKey` ,identity f" +
                " WHERE f.`ID`=a.`IdentityID` GROUP BY f.`FixedRef`";

        jdbcTemplate.update(sql);
    }
    @Override
    public void executeprogramInsertQuery() {
        String sql = " INSERT INTO san_sort_program" +
                " SELECT ids,'Program' AS heading ,a.textdata AS val FROM " +
                " (SELECT a.`ProfileID` AS ids,GROUP_CONCAT(b.`Comment`) AS textdata,1" +
                " FROM `sanctionctionsentry` a" +
                " JOIN `sanctionsmeasure` b ON a.`PrimaryKey` = b.`FK_SanctionsEntry`" +
                " JOIN `sanctionstype` c ON b.`SanctionsTypeID` = c.`ID`" +
                " WHERE b.`Comment` IS NOT NULL GROUP BY 1) a";

        jdbcTemplate.update(sql);
    }
    @Override
    public void executeTypeInsertQuery() {
        String sql = " INSERT INTO san_sort_type" +
                " SELECT a.`ID` AS ids,'Type' AS heading," +
                " (SELECT IF(b.Text='Unknown',a.Text,b.Text) AS val" +
                " FROM partytype a,partysubtype b WHERE a.ID=b.PartyTypeID AND b.`ID`=PartySubTypeID)" +
                " AS val FROM PROFILE a GROUP BY a.`ID`";

        jdbcTemplate.update(sql);
    }
}
