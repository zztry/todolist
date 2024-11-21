package com.todolist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskSchedule {
    private int id;
    private int taskId;
    private int userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
