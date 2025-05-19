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
@Table(name = "unsc_aliases")
public class IndividualAlias extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "individual_id", nullable = false)
    private Individual individual;

    @Column(name = "alias_name")
    private String aliasName;

    @Column(name = "quality")
    private String quality;

    @Column(name = "city_of_birth")
    private String cityOfBirth;

    @Column(name = "country_of_birth")
    private String countryOfBirth;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    public static IndividualAlias create(Individual individual, String name, String quality, String cityOfBirth, String countryOfBirth, String dateOfBirth, String note) {
        final IndividualAlias individualAlias = new IndividualAlias();
        individualAlias.setStatus(Status.ACTIVE);
        individualAlias.setCreatedAt(LocalDateTime.now());
        individualAlias.setIndividual(individual);
        individualAlias.setAliasName(name);
        individualAlias.setQuality(quality);
        individualAlias.setNote(note);
        individualAlias.setAliasName(cityOfBirth);
        individualAlias.setQuality(countryOfBirth);
        individualAlias.setNote(dateOfBirth);
        return individualAlias;
    }

    public static List<IndividualAlias> from(Map<String, Object> row, Individual individual){
        List<IndividualAlias> individualAliases = new ArrayList<>();
        int i = 0;
        while (row.containsKey("INDIVIDUAL_ALIAS/" + i + "/ALIAS_NAME")) {
            String quality = getString(row, "INDIVIDUAL_ALIAS/" + i + "/QUALITY");
            String aliasName = getString(row, "INDIVIDUAL_ALIAS/" + i + "/ALIAS_NAME");
            String country = getString(row, "INDIVIDUAL_ALIAS/" + i + "/COUNTRY_OF_BIRTH");
            String date = getString(row, "INDIVIDUAL_ALIAS/" + i + "/DATE_OF_BIRTH");
            String city = getString(row, "INDIVIDUAL_ALIAS/" + i + "/CITY_OF_BIRTH");
            String note = getString(row, "INDIVIDUAL_ALIAS/" + i + "/NOTE");
            if (aliasName != null && !aliasName.trim().isEmpty()) {
                individualAliases.add(create(individual, aliasName, quality, city, country, date, note));
            }
            i++;
        }
        return individualAliases;

    }

    public static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null || value.toString().trim().isEmpty()) {
            return null;
        }
        return value.toString();
    }
}
