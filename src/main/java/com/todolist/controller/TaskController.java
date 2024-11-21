package com.todolist.controller;

import com.todolist.config.UserSession;
import com.todolist.dao.TaskDao;
import com.todolist.dao.TaskScheduleDao;
import com.todolist.dao.TaskTagDao;
import com.todolist.dao.UserDao;
import com.todolist.entity.Tag;
import com.todolist.entity.Task;
import com.todolist.entity.TaskSchedule;
import com.todolist.entity.TaskTag;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TaskController {

    /**
     * 对外的创建task接口
     */
    public static void addTask(String title, String description, LocalDate startDate, LocalDate endDate,
                               LocalTime startTime, LocalTime endTime, int priority, int repeatType,
                               int repeatInterval, int repeatNum, List<String> tagsName) {
        // 创建任务对象
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setStartTime(startTime);
        task.setEndTime(endTime);
        task.setPriority(priority);
        task.setRepeatType(repeatType);
        task.setRepeatInterval(repeatInterval);
        task.setRepeatNum(repeatNum);
        task.setUserId(UserSession.getInstance().getCurrentUser().getId());

        // 获取标签ID列表
        List<Integer> tagsId = new ArrayList<>();
        for (String tName : tagsName) {
            int id = TaskDao.getIdByName(tName);
            if(id==-1){
                System.out.println("当前tag不存在："+tName);
                continue;
            }
            tagsId.add(id);
        }

        // 插入任务和标签
        insert(task, tagsId);
    }

    /**
     * 创建一个新任务
     * @param task
     */
    public static void insert(Task task, List<Integer> tagsId) {
        //插入task到表中
        int taskId = TaskDao.insert(task);
        //插入tags-tag到表中
        for (int tagId : tagsId) {
            //构造TaskTag类型变量，插入到表中
            TaskTag taskTag = new TaskTag();
            taskTag.setTask_id(taskId);
            taskTag.setTag_id(tagId);
            taskTag.setUser_id(UserSession.getInstance().getCurrentUser().getId());
            TaskTagDao.insert(taskTag);
        }
        //插入task_schedule表
        TaskSchedule taskSchedule = new TaskSchedule();
        taskSchedule.setTaskId(taskId);
        taskSchedule.setUserId(UserSession.getInstance().getCurrentUser().getId());
        //start time
        LocalDate startDate = task.getStartDate();
        LocalTime startTime = task.getStartTime();
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        taskSchedule.setStartTime(startDateTime);
        //end time
        LocalDate endDate = task.getEndDate();
        LocalTime endTime = task.getEndTime();
        LocalDateTime endDateTime = LocalDateTime.of(endDate,endTime);
        taskSchedule.setEndTime(endDateTime);

        TaskScheduleDao.insert(taskSchedule);

    }
}
