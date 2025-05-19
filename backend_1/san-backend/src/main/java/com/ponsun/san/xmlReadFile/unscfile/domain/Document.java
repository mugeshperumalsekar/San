package com.ponsun.san.xmlReadFile.unscfile.domain;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "unsc_documents")
public class Document extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "individual_id", nullable = false)
    private Individual individual;

    @Column(name = "type")
    private String type;

    @Column(name = "type_2")
    private String type2;

    @Column(name = "country_of_issue")
    private String countryOfIssue;

    @Column(name = "number")
    private String number;

    @Column(name = "issuing_country")
    private String issuingCountry;

    @Column(name = "date_of_issue")
    private String dateOfIssue;

    @Column(name = "city_of_issue")
    private String cityOfIssue;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    public static Document create(Individual individual, String type, String type2, String countryOfIssue, String number, String issuingCountry,
                                  String dateOfIssue, String cityOfIssue, String note) {
        Document document = new Document();
        document.setIndividual(individual);
        document.setType(type);
        document.setType(type2);
        document.setType(countryOfIssue);
        document.setNumber(number);
        document.setIssuingCountry(issuingCountry);
        document.setDateOfIssue(dateOfIssue);
        document.setCityOfIssue(cityOfIssue);
        document.setNote(note);
        document.setStatus(Status.ACTIVE);
        document.setCreatedAt(LocalDateTime.now());
        return document;
    }

    public static List<Document> from(Map<String, Object> row, Individual individual){
        List<Document> documents = new ArrayList<>();
        int i = 0;
        while (row.containsKey("INDIVIDUAL_DOCUMENT/" + i + "/TYPE_OF_DOCUMENT") ||
                row.containsKey("INDIVIDUAL_DOCUMENT/" + i + "/NUMBER") ||
                row.containsKey("INDIVIDUAL_DOCUMENT/" + i + "/ISSUING_COUNTRY") ||
                row.containsKey("INDIVIDUAL_DOCUMENT/" + i + "/DATE_OF_ISSUE") ||
                row.containsKey("INDIVIDUAL_DOCUMENT/" + i + "/CITY_OF_ISSUE") ||
                row.containsKey("INDIVIDUAL_DOCUMENT/" + i + "/NOTE")) {

            String type = getString(row, "INDIVIDUAL_DOCUMENT/" + i + "/TYPE_OF_DOCUMENT");
            String type2 = getString(row, "INDIVIDUAL_DOCUMENT/" + i + "/TYPE_OF_DOCUMENT2");
            String number = getString(row, "INDIVIDUAL_DOCUMENT/" + i + "/NUMBER");
            String countryOfIssue = getString(row, "INDIVIDUAL_DOCUMENT/" + i + "/COUNTRY_OF_ISSUE");
            String issuingCountry = getString(row, "INDIVIDUAL_DOCUMENT/" + i + "/ISSUING_COUNTRY");
            String dateOfIssue = getString(row, "INDIVIDUAL_DOCUMENT/" + i + "/DATE_OF_ISSUE");
            String cityOfIssue = getString(row, "INDIVIDUAL_DOCUMENT/" + i + "/CITY_OF_ISSUE");
            String note = getString(row, "INDIVIDUAL_DOCUMENT/" + i + "/NOTE");

            if (type != null || number != null || issuingCountry != null || dateOfIssue != null || cityOfIssue != null || note != null) {
                documents.add(create(individual, type, type2, countryOfIssue, number, issuingCountry, dateOfIssue, cityOfIssue, note));
            }
            i++;
        }
        return documents;

    }

    public static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null || value.toString().trim().isEmpty()) {
            return null;
        }
        return value.toString();
    }

}
