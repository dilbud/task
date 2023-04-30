package com.dbx.task.controller;

import com.dbx.task.service.TaskService;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Validated
@CrossOrigin("*")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("")
    public LocalDate getEndDate(
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = true)  @Min(1) Integer days) {
        return taskService.getEndDate(startDate, days);
    }
}