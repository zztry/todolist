package com.todolist.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int priority;
    private int repeatType;
    private int repeatInterval;
    private int repeatNum;
    private int status;
    private int userId;



    public String toString2() {
        return "任务信息：{" +
                "任务ID=" + id +
                ", 任务名称='" + title + '\'' +
                ", 任务描述='" + description + '\'' +
                ", 开始日期=" + startDate +
                ", 结束日期=" + endDate +
                ", 开始时间=" + startTime +
                ", 结束时间=" + endTime +
                ", 优先级=" + priority + "（1最高）" +
                ", 是否重复=" + (repeatType == 0 ? "不重复" : "重复") +
                ", 重复间隔=" + repeatInterval + "天" +
                ", 重复次数=" + repeatNum +
                ", 任务状态=" + (status == 0 ? "未完成" : status == 1 ? "已完成" : "逾期未完成") +
                ", 用户ID=" + userId +
                '}';
    }

}
