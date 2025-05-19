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
@Table(name = "unsc_nationalities")
public class Nationality extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "individual_id", nullable = false)
    private Individual individual;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    public static Nationality create(Individual individual, String name) {
        final Nationality nationality = new Nationality();
        nationality.setStatus(Status.ACTIVE);
        nationality.setCreatedAt(LocalDateTime.now());
        nationality.setIndividual(individual);
        nationality.setNationality(name);
        return nationality;
    }

    public static List<Nationality> from(Map<String, Object> row, Individual individual){
        List<Nationality> nationalities = new ArrayList<>();
        int i = 0;
        while (row.containsKey("NATIONALITY/VALUE/" + i)) {
            String name = getString(row, "NATIONALITY/VALUE/" + i);
            if (name != null && !name.trim().isEmpty()) {
                nationalities.add(create(individual, name));
            }
            i++;
        }
        return nationalities;

    }

    public static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null || value.toString().trim().isEmpty()) {
            return null;
        }
        return value.toString();
    }
}
