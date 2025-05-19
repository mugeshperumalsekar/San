package com.ponsun.san.xmlReadFile.unscfile.domain;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "unsc_dates_of_birth")
public class DateOfBirth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "individual_id", nullable = false)
    private Individual individual;

    @Column(name = "type_of_date")
    private String typeOfDate;

    @Column(name = "date")
    private String date;

    @Column(name = "year_from")
    private String yearFrom;

    @Column(name = "year_to")
    private String yearTo;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    public static DateOfBirth create(Individual individual, String typeOfDate, String date, String yearFrom, String yearTo, String note) {
        DateOfBirth dateOfBirth = new DateOfBirth();
        dateOfBirth.setIndividual(individual);
        dateOfBirth.setTypeOfDate(typeOfDate);
        dateOfBirth.setDate(date);
        dateOfBirth.setYearFrom(yearFrom);
        dateOfBirth.setYearTo(yearTo);
        dateOfBirth.setNote(note);
        dateOfBirth.setStatus(Status.ACTIVE);
        dateOfBirth.setCreatedAt(LocalDateTime.now());
        return dateOfBirth;
    }

    public static List<DateOfBirth> from(Map<String, Object> row, Individual individual){
        List<DateOfBirth> datesOfBirth = new ArrayList<>();
        int i = 0;
        while (row.containsKey("INDIVIDUAL_DATE_OF_BIRTH/" + i + "/DATE") ||
                row.containsKey("INDIVIDUAL_DATE_OF_BIRTH/" + i + "/TYPE_OF_DATE") ||
                row.containsKey("INDIVIDUAL_DATE_OF_BIRTH/" + i + "/FROM_YEAR")) {

            String typeOfDate = getString(row, "INDIVIDUAL_DATE_OF_BIRTH/" + i + "/TYPE_OF_DATE");
            String fromYear = getString(row, "INDIVIDUAL_DATE_OF_BIRTH/" + i + "/FROM_YEAR");
            String toYear = getString(row, "INDIVIDUAL_DATE_OF_BIRTH/" + i + "/TO_YEAR");
            String note = getString(row, "INDIVIDUAL_DATE_OF_BIRTH/" + i + "/NOTE");
            String date = getString(row, "INDIVIDUAL_DATE_OF_BIRTH/" + i + "/DATE");

            if (date != null || typeOfDate != null || fromYear != null) {
                datesOfBirth.add(create(individual, typeOfDate, date, fromYear, toYear, note));
            }
            i++;
        }
        return datesOfBirth;

    }

    public static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null || value.toString().trim().isEmpty()) {
            return null;
        }
        return value.toString();
    }

}
