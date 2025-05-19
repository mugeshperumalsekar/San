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
@Table(name = "unsc_addresses")
public class Address extends BaseEntity {

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

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "zip_code")
    private String zipCode;

    public static Address create(Individual individual, String street, String city, String state, String country, String zipCode) {
        final Address address = new Address();
        address.setStatus(Status.ACTIVE);
        address.setCreatedAt(LocalDateTime.now());
        address.setIndividual(individual);
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setCountry(country);
        address.setZipCode(zipCode);
        return address;
    }

    public static List<Address> from(Map<String, Object> row, Individual individual){
        List<Address> addresses = new ArrayList<>();
        int i = 0;
        while (row.containsKey("INDIVIDUAL_ADDRESS/" + i + "/COUNTRY")) {
            String street = getString(row, "INDIVIDUAL_ADDRESS/" + i + "/STREET");
            String city = getString(row, "INDIVIDUAL_ADDRESS/" + i + "/CITY");
            String state = getString(row, "INDIVIDUAL_ADDRESS/" + i + "/STATE_PROVINCE");
            String country = getString(row, "INDIVIDUAL_ADDRESS/" + i + "/COUNTRY");
            String zipCode = getString(row, "INDIVIDUAL_ADDRESS/" + i + "/ZIP_CODE");
            if (country != null && !country.trim().isEmpty()) {
                addresses.add(create(individual, street, city, state, country, zipCode));
            }
            i++;
        }
        return addresses;

    }

    public static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null || value.toString().trim().isEmpty()) {
            return null;
        }
        return value.toString();
    }
}
