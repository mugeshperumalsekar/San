package com.ponsun.san.xmlReadFile.unscfile.entity.dateupdate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DateUpdateRepository extends JpaRepository<DateUpdate, Integer> {

    @Query("SELECT e FROM DateUpdate e WHERE e.id=:id AND e.status = 'A'")
    Optional<DateUpdate> findOneWithId(@Param("id") Integer id);
}
