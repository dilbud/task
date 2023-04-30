package com.dbx.task.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class TaskServiceHelperTest {

    @InjectMocks
    private TaskServiceHelper taskServiceHelper;

    private static List<LocalDate> listOfWeekEndDays;

    private static List<LocalDate> listOfWeekDays;

    @BeforeAll
    public static void init() {
        listOfWeekEndDays = List.of(
                LocalDate.parse("2023-04-01"),
                LocalDate.parse("2023-04-02"),
                LocalDate.parse("2023-04-08"),
                LocalDate.parse("2023-04-09"),
                LocalDate.parse("2023-04-15"),
                LocalDate.parse("2023-04-16"),
                LocalDate.parse("2023-04-22"),
                LocalDate.parse("2023-04-23"),
                LocalDate.parse("2023-04-29"),
                LocalDate.parse("2023-04-30"));

        listOfWeekDays = List.of(
                LocalDate.parse("2023-04-03"),
                LocalDate.parse("2023-04-04"),
                LocalDate.parse("2023-04-05"),
                LocalDate.parse("2023-04-06"),
                LocalDate.parse("2023-04-07"),
                LocalDate.parse("2023-04-10"),
                LocalDate.parse("2023-04-11"),
                LocalDate.parse("2023-04-12"),
                LocalDate.parse("2023-04-13"),
                LocalDate.parse("2023-04-14"),
                LocalDate.parse("2023-04-17"),
                LocalDate.parse("2023-04-18"),
                LocalDate.parse("2023-04-19"),
                LocalDate.parse("2023-04-20"),
                LocalDate.parse("2023-04-21"),
                LocalDate.parse("2023-04-24"),
                LocalDate.parse("2023-04-25"),
                LocalDate.parse("2023-04-26"),
                LocalDate.parse("2023-04-27"),
                LocalDate.parse("2023-04-28"));
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void numberOfWeekEndDaysInRange() {
        assertEquals(10,
                taskServiceHelper.numberOfWeekEndDaysInRange(LocalDate.parse("2023-04-01"), LocalDate.parse("2023-04-30")));

        assertEquals(4,
                taskServiceHelper.numberOfWeekEndDaysInRange(LocalDate.parse("2023-04-03"), LocalDate.parse("2023-04-17")));
    }

    @Test
    void isWeekend() {

        assertEquals(10, listOfWeekEndDays.size());
        assertEquals(20, listOfWeekDays.size());

        assertEquals(true,
                listOfWeekEndDays.stream().map(taskServiceHelper::isWeekend).reduce(true, Boolean::logicalAnd));
        assertEquals(true,
                listOfWeekDays.stream().map(taskServiceHelper::isWeekend).map((v)->!v).reduce(true, Boolean::logicalAnd));
    }
}