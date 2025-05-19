package com.ponsun.san.xmlReadFile.unscfile.entity.dateupdate;

import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass1.EntityClass1;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "date_updated")
public class DateUpdate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "entity_class1_id")
    private EntityClass1 entityClass1;

    @Column(name = "last_day_updated")
    private String lastDayUpdated;

}

