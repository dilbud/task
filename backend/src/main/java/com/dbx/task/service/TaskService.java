package com.dbx.task.service;

import com.dbx.task.model.Event;
import com.dbx.task.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final EventRepository eventRepository;
    private final TaskServiceHelper taskServiceHelper;

    TaskService(EventRepository eventRepository, TaskServiceHelper taskServiceHelper) {
        this.eventRepository = eventRepository;
        this.taskServiceHelper = taskServiceHelper;
    }

    public LocalDate getEndDate(LocalDate startDate, Integer days) {
        LocalDate endDateLocal = startDate.plusDays(days);
        LocalDate startDateLocal = startDate.plusDays(1);

        boolean isPresent;

        do {
            List<Event> eventList =
                    eventRepository.findEventByBetweenStartDateAndEndDate(startDateLocal, endDateLocal);
            int weekEndDays = taskServiceHelper.numberOfWeekEndDaysInRange(
                    LocalDate.of(startDateLocal.getYear(), startDateLocal.getMonth(), startDateLocal.getDayOfMonth()),
                    LocalDate.of(endDateLocal.getYear(), endDateLocal.getMonth(), endDateLocal.getDayOfMonth()));

            int sumOfNonWorkDays;

            if (eventList.isEmpty() || weekEndDays == 0) {
                sumOfNonWorkDays = weekEndDays + eventList.size();
            } else {
                int sumOfNonWorkDaysDuplicates = eventList.size() + weekEndDays;
                int common = (int) eventList.stream().map(Event::getDate).filter(taskServiceHelper::isWeekend).count();
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


}
