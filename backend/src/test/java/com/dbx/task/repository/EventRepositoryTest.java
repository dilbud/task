package com.dbx.task.repository;

import com.dbx.task.model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatRuntimeException;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void injectNotNull() {
        assertThat(eventRepository).isNotNull();
    }

    @Test
    void typeCheck() {
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(51);
        assertThat(eventList).hasOnlyElementsOfType(Event.class);
        assertThat(eventList).doesNotContainNull();
    }

    @Test
    void findEventByBetweenStartDateAndEndDateTest01() {
        List<Event> eventList = eventRepository
                .findEventByBetweenStartDateAndEndDate(
                        LocalDate.parse("2022-01-17"),
                        LocalDate.parse("2022-01-17"));
        assertThat(eventList).hasSize(1);
    }

    @Test
    void findEventByBetweenStartDateAndEndDateTest02() {
        List<Event> eventList = eventRepository
                .findEventByBetweenStartDateAndEndDate(
                        LocalDate.parse("2022-02-04"),
                        LocalDate.parse("2022-04-13"));
        assertThat(eventList).hasSize(5);
    }

    @Test
    void findEventByBetweenStartDateAndEndDateTest03() {
        List<Event> eventList = eventRepository
                .findEventByBetweenStartDateAndEndDate(
                        LocalDate.parse("2022-01-04"),
                        LocalDate.parse("2022-12-10"));
        assertThat(eventList).hasSize(24);
    }

    @Test
    void findEventByBetweenStartDateAndEndDateTest04() {
        List<Event> eventList = eventRepository
                .findEventByBetweenStartDateAndEndDate(
                        LocalDate.parse("2022-12-01"),
                        LocalDate.parse("2022-12-28"));
        assertThat(eventList).hasSize(2);
    }

    @Test
    void findEventByBetweenStartDateAndEndDateTest05() {
        List<Event> eventList = eventRepository
                .findEventByBetweenStartDateAndEndDate(
                        LocalDate.parse("2022-02-04"),
                        LocalDate.parse("2023-04-01"));
        assertThat(eventList).hasSize(29);
    }

    @Test
    void findEventByBetweenStartDateAndEndDateTest06() {
        List<Event> eventList = eventRepository
                .findEventByBetweenStartDateAndEndDate(
                        LocalDate.parse("2023-02-04"),
                        LocalDate.parse("2022-04-13"));
        assertThat(eventList).hasSize(0);
    }

    @Test
    void findAll() {
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(51);
    }
}