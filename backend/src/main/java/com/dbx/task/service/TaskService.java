package com.dbx.task.service;

import com.dbx.task.model.Event;
import com.dbx.task.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

@Service
public class TaskService {

    private final EventRepository eventRepository;

    TaskService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public LocalDate getEndDate(LocalDate startDate, Integer days) {
        LocalDate endDateLocal = startDate.plusDays(days);
        LocalDate startDateLocal = startDate.plusDays(1);

        boolean isPresent;

        do {
            List<Event> eventList =
                    eventRepository.findEventByBetweenStartDateAndEndDate(startDateLocal, endDateLocal);
            int weekEndDays = numberOfWeekEndDaysInRange(
                    LocalDate.of(startDateLocal.getYear(), startDateLocal.getMonth(), startDateLocal.getDayOfMonth()),
                    LocalDate.of(endDateLocal.getYear(), endDateLocal.getMonth(), endDateLocal.getDayOfMonth()));

            int sumOfNonWorkDays;

            if (eventList.isEmpty() || weekEndDays == 0) {
                sumOfNonWorkDays = weekEndDays + eventList.size();
            } else {
                int sumOfNonWorkDaysDuplicates = eventList.size() + weekEndDays;
                int common = (int) eventList.stream().map(Event::getDate).filter(this::isWeekend).count();
                sumOfNonWorkDays = sumOfNonWorkDaysDuplicates - common;
            }

            if (sumOfNonWorkDays > 0) {
                isPresent = true;
                startDateLocal = endDateLocal.plusDays(1);
                endDateLocal = endDateLocal.plusDays(sumOfNonWorkDays);
            } else {
                isPresent = false;
            }
        } while (isPresent);
        return endDateLocal;
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
