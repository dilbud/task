package com.dbx.task.service;

import com.dbx.task.model.Event;
import com.dbx.task.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private  List<Event> eventListExpect;

    @BeforeEach
    void setUp() {
        Event ev1 = new Event(1L, LocalDate.parse("2022-01-14"), "Tamil Thai Pongal Day");
        Event ev2 = new Event(2L, LocalDate.parse("2022-01-17"), "Duruthu Full Moon Poya Day");
        Event ev3 = new Event(3L, LocalDate.parse("2022-02-04"), "National Day");
        Event ev4 = new Event(4L, LocalDate.parse("2022-02-16"), "Navam Full Moon Poya Day");
        Event ev5 = new Event(5L, LocalDate.parse("2022-03-01"), "Mahasivarathri Day");
        Event ev6 = new Event(6L, LocalDate.parse("2022-03-17"), "Madin Full Moon Poya Day");
        Event ev7 = new Event(7L, LocalDate.parse("2022-04-13"), "Day prior to Sinhala & Tamil New Year Day");
        Event ev8 = new Event(8L, LocalDate.parse("2022-04-14"), "Sinhala & Tamil New Year Day");
        Event ev9 = new Event(9L, LocalDate.parse("2022-04-15"), "Good Friday");
        Event ev10 = new Event(10L, LocalDate.parse("2022-04-16"), "Bak Full Moon Poya Day");
        Event ev11 = new Event(11L, LocalDate.parse("2022-05-01"), "May Day");
        Event ev12 = new Event(12L, LocalDate.parse("2022-05-03"), "Id Ul-Fitr");

        eventListExpect = List.of(
                ev1,
                ev2,
                ev3,
                ev4,
                ev5,
                ev6,
                ev7,
                ev8,
                ev9,
                ev10,
                ev11,
                ev12
        );

        when(eventRepository.findAll()).thenReturn(this.eventListExpect);
    }

    @AfterEach
    void tearDown() {
        this.eventListExpect = null;
    }

    @Test
    void getAllEvent() {
        List<Event> eventList = this.eventService.getAllEvent();
        assertEquals(this.eventListExpect, eventList);
        verify(this.eventRepository).findAll();
        assertThat(eventList)
                .hasSize(12)
                .hasOnlyElementsOfType(Event.class)
                .isInstanceOf(List.class);
    }
}