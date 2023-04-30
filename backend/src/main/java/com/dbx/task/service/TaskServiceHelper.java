package com.dbx.task.service;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

@Component
public class TaskServiceHelper {

    TaskServiceHelper() {

    }

    public int numberOfWeekEndDaysInRange(LocalDate startDate, LocalDate endDate) {
        int numberOfDays = 0;
        while (startDate.isBefore(endDate.plusDays(1))) {
            if(isWeekend(startDate)) {
                ++numberOfDays;
            }
            startDate = startDate.plusDays(1);
        }
        return numberOfDays;
    }

    public boolean isWeekend(LocalDate ld) {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }
}
