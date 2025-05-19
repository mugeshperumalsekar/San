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
@Table(name = "unsc_places_of_birth")
public class PlaceOfBirth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "individual_id", nullable = false)
    private Individual individual;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    public static PlaceOfBirth create(Individual individual, String street, String city, String state, String country, String note) {
        PlaceOfBirth placeOfBirth = new PlaceOfBirth();
        placeOfBirth.setIndividual(individual);
        placeOfBirth.setStreet(street);
        placeOfBirth.setCity(city);
        placeOfBirth.setState(state);
        placeOfBirth.setCountry(country);
        placeOfBirth.setNote(note);
        placeOfBirth.setStatus(Status.ACTIVE);
        placeOfBirth.setCreatedAt(LocalDateTime.now());
        return placeOfBirth;
    }

    public static List<PlaceOfBirth> from(Map<String, Object> row, Individual individual){
        List<PlaceOfBirth> placesOfBirth = new ArrayList<>();
        int i = 0;
        while (row.containsKey("INDIVIDUAL_PLACE_OF_BIRTH/" + i + "/COUNTRY") ||
                row.containsKey("INDIVIDUAL_PLACE_OF_BIRTH/" + i + "/STATE_PROVINCE") ||
                row.containsKey("INDIVIDUAL_PLACE_OF_BIRTH/" + i + "/CITY") ||
                row.containsKey("INDIVIDUAL_PLACE_OF_BIRTH/" + i + "/STREET")) {

            String street = getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/" + i + "/STREET");
            String city = getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/" + i + "/CITY");
            String state = getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/" + i + "/STATE_PROVINCE");
            String country = getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/" + i + "/COUNTRY");
            String note = getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/" + i + "/NOTE");

            if (country != null || state != null || city != null || street != null) {
                placesOfBirth.add(create(individual, street, city, state, country, note));
            }
            i++;
        }
        return placesOfBirth;

    }

    public static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null || value.toString().trim().isEmpty()) {
            return null;
        }
        return value.toString();
    }

}
