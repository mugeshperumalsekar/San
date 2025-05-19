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
@Table(name = "unsc_last_updates")
public class LastUpdate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "individual_id", nullable = false)
    private Individual individual;

    @Column(name = "update_date")
    private String updateDate;

    public static LastUpdate create(Individual individual, String updateDate) {
        LastUpdate lastUpdate = new LastUpdate();
        lastUpdate.setIndividual(individual);
        lastUpdate.setUpdateDate(updateDate);
        lastUpdate.setStatus(Status.ACTIVE);
        lastUpdate.setCreatedAt(LocalDateTime.now());
        return lastUpdate;
    }

    public static List<LastUpdate> from(Map<String, Object> row, Individual individual){
        List<LastUpdate> lastUpdates = new ArrayList<>();
        int i = 0;
        while (row.containsKey("LAST_DAY_UPDATED/VALUE/" + i)) {
            String updateDate = getString(row, "LAST_DAY_UPDATED/VALUE/" + i);
            if (updateDate != null && !updateDate.trim().isEmpty()) {
                LastUpdate lastUpdate = LastUpdate.create(individual, updateDate);
                lastUpdates.add(lastUpdate);
            }
            i++;
        }
        return lastUpdates;
    }

    public static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null || value.toString().trim().isEmpty()) {
            return null;
        }
        return value.toString();
    }

}
