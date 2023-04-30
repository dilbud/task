package com.dbx.task.controller;

import com.dbx.task.model.Event;
import com.dbx.task.service.EventService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private EventService eventService;

    private List<Event> eventListExpect;

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();


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
        when(eventService.getAllEvent())
                .thenReturn(this.eventListExpect);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void injectTest() {
        assertThat(mvc).isNotNull();
        assertThat(eventListExpect)
                .isNotNull()
                .doesNotContainNull()
                .hasSize(12)
                .isInstanceOf(List.class)
                .hasOnlyElementsOfType(Event.class)
                .isNotEmpty()
                .doesNotContainNull();
        assertThat(eventService).isNotNull();
    }

    @Test
    void getEvents() throws Exception {
        assertThat(eventService.getAllEvent()).hasSize(12);
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/events")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertThat(objectMapper.readValue(response, new TypeReference<List<Event>>(){}))
                .isNotNull()
                .isNotEmpty()
                .hasSize(12)
                .isInstanceOf(List.class)
                .doesNotContainNull()
                .hasOnlyElementsOfType(Event.class);

    }
}