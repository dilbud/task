package com.dbx.task.service;

import com.dbx.task.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TaskServiceTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public TaskServiceHelper taskServiceHelper() {
            return new TaskServiceHelper();
        }
        @Bean
        public TaskService taskService(EventRepository eventRepository, TaskServiceHelper taskServiceHelper) {
            return new TaskService(eventRepository, taskServiceHelper);
        }
    }


    @Autowired
    private TaskService taskService;

    public static Map<Integer, LocalDate> map = new HashMap<>();

    @BeforeAll
    public static void init() {
        map.put(1, LocalDate.parse("2023-04-03"));
        map.put(2, LocalDate.parse("2023-04-04"));
        map.put(3, LocalDate.parse("2023-04-06"));
        map.put(4, LocalDate.parse("2023-04-10"));
        map.put(5, LocalDate.parse("2023-04-11"));
        map.put(6, LocalDate.parse("2023-04-12"));
        map.put(7, LocalDate.parse("2023-04-17"));
        map.put(8, LocalDate.parse("2023-04-18"));
        map.put(9, LocalDate.parse("2023-04-19"));
        map.put(10, LocalDate.parse("2023-04-20"));
        map.put(11, LocalDate.parse("2023-04-21"));
        map.put(12, LocalDate.parse("2023-04-24"));
        map.put(13, LocalDate.parse("2023-04-25"));
        map.put(14, LocalDate.parse("2023-04-26"));
        map.put(15, LocalDate.parse("2023-04-27"));
        map.put(16, LocalDate.parse("2023-04-28"));
        map.put(17, LocalDate.parse("2023-05-02"));
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void injectTest() {
        assertThat(taskService).isNotNull();
    }

    @Test
    void getEndDate01() {
        IntStream.rangeClosed(1, 17).forEach((v)->{
            LocalDate endDate = taskService.getEndDate(LocalDate.parse("2023-04-01"), v);
            assertThat(endDate).isEqualTo(map.get(v));
        });
    }

}