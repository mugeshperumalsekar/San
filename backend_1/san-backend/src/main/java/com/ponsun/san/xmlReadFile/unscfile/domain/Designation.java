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
@Table(name = "unsc_designations")
public class Designation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "individual_id", nullable = false)
    private Individual individual;

    @Column(name = "designation", nullable = false)
    private String designation;

    public static Designation create(Individual individual, String designationName) {
        final Designation designation = new Designation();
        designation.setStatus(Status.ACTIVE);
        designation.setCreatedAt(LocalDateTime.now());
        designation.setIndividual(individual);
        designation.setDesignation(designationName);
        return designation;
    }
    public static List<Designation> from(Map<String, Object> row, Individual individual){
        List<Designation> designations = new ArrayList<>();
        int i = 0;

        while (row.containsKey("DESIGNATION/VALUE/" + i)) {
            String name = getString(row, "DESIGNATION/VALUE/" + i);
            if (name != null && !name.trim().isEmpty()) {
                designations.add(create(individual, name));
            }
            i++;
        }
        return designations;

    }

    public static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null || value.toString().trim().isEmpty()) {
            return null;
        }
        return value.toString();
    }

}
