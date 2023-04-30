package com.dbx.task.repository;

import com.dbx.task.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT e FROM Event e " +
            "WHERE " +
            "e.date BETWEEN :startDate AND :endDate")
    List<Event> findEventByBetweenStartDateAndEndDate(
            @Param("startDate")  LocalDate startDate,
            @Param("endDate")  LocalDate endDate);
}