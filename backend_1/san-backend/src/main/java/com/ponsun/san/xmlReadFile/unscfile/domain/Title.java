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
@Table(name = "unsc_titles")
public class Title extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "individual_id", nullable = false)
    private Individual individual;

    @Column(name = "title", nullable = false)
    private String title;

    public static Title create(Individual individual, String name) {
        final Title title = new Title();
        title.setTitle(name);
        title.setIndividual(individual);
        title.setStatus(Status.ACTIVE);
        title.setCreatedAt(LocalDateTime.now());
        return title;
    }

    public static List<Title> from(Map<String, Object> row, Individual individual){
        List<Title> titles = new ArrayList<>();
        int i = 0;
        while (row.containsKey("TITLE/VALUE/" + i)) {
            String name = getString(row, "TITLE/VALUE/" + i);
            if (name != null && !name.trim().isEmpty()) {
                titles.add(create(individual, name));
            }
            i++;
        }
        return titles;

    }

    public static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null || value.toString().trim().isEmpty()) {
            return null;
        }
        return value.toString();
    }


}
