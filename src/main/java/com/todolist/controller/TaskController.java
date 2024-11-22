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
import java.time.temporal.ChronoUnit;
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
        //插入task_schedule表 ，如果是重复任务，重复插入多个
        int userId = UserSession.getInstance().getCurrentUser().getId();
        LocalDate startDate = task.getStartDate();
        LocalDate endDate = task.getEndDate();
        LocalTime startTime = task.getStartTime();
        LocalTime endTime = task.getEndTime();
        int repeatType = task.getRepeatType();
        int repeatInterval = task.getRepeatInterval();
        int repeatNum = task.getRepeatNum();

        List<LocalDateTime> startDateTimes = generateScheduleDates(startDate, endDate, repeatType, repeatInterval, repeatNum);
        for (LocalDateTime startDateTime : startDateTimes) {
            TaskSchedule taskSchedule = new TaskSchedule();
            taskSchedule.setTaskId(taskId);
            taskSchedule.setUserId(userId);
            taskSchedule.setStartTime(startDateTime);
            taskSchedule.setEndTime(startDateTime.plusHours(endTime.getHour() - startTime.getHour())
                    .plusMinutes(endTime.getMinute() - startTime.getMinute()));
            TaskScheduleDao.insert(taskSchedule);
        }

    }

    /**
     * 寻找当前的全部任务
     * @return
     */
    public static List<Task> selectTodayTask(){
        List<Task> tasks = new ArrayList<>();
        LocalDate today = LocalDate.now();
        List<TaskSchedule> todayTaskSchedules = TaskScheduleDao.taskInDate(today);

        for (TaskSchedule schedule : todayTaskSchedules) {
            Task task = TaskDao.getTaskById(schedule.getTaskId());
            if (task != null) {
                tasks.add(task);
            }
        }

        return tasks;

    }

    private static List<LocalDateTime> generateScheduleDates(LocalDate startDate, LocalDate endDate, int repeatType, int repeatInterval, int repeatNum) {
        List<LocalDateTime> dates = new ArrayList<>();
        LocalDate current = startDate;
        while (!current.isAfter(endDate) && repeatNum > 0) {
            LocalDateTime startDateTime = current.atStartOfDay();
            dates.add(startDateTime);
            current = current.plus(repeatInterval, ChronoUnit.DAYS);
            repeatNum--;
        }
        return dates;
    }

}
