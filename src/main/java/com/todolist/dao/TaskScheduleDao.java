package com.todolist.dao;

import com.todolist.entity.TaskSchedule;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskScheduleDao {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/database/todolist.db"; // 数据库文件的路径

    /**
     * 插入，返回id
     * @param taskSchedule
     * @return
     */
    public static int insert(TaskSchedule taskSchedule){
        String sql = "INSERT INTO task_schedule (task_id, user_id, start_time, end_time) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, taskSchedule.getTaskId());
            pstmt.setInt(2, taskSchedule.getUserId());
            pstmt.setTimestamp(3, Timestamp.valueOf(taskSchedule.getStartTime()));
            pstmt.setTimestamp(4, Timestamp.valueOf(taskSchedule.getEndTime()));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if insertion fails or no ID is generated
    }

    /**
     * 查找开始于date当天的任务
     */
    public static List<TaskSchedule> taskInDate(LocalDate date){
        List<TaskSchedule> taskSchedules = new ArrayList<>();
        String sql = "SELECT * FROM task_schedule WHERE start_time >= ? AND start_time < ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = startOfDay.plusDays(1);
            pstmt.setTimestamp(1, Timestamp.valueOf(startOfDay));
            pstmt.setTimestamp(2, Timestamp.valueOf(endOfDay));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    taskSchedules.add(mapRowToTaskSchedule(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskSchedules;
    }

    /**
     * 查找结束时间在date当天以及之前的任务
     */
    public static List<TaskSchedule> TaskBeforeDate(LocalDate date){
        List<TaskSchedule> taskSchedules = new ArrayList<>();
        String sql = "SELECT * FROM task_schedule WHERE end_time <= ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            LocalDateTime endOfDay = date.atStartOfDay().plusDays(1);
            pstmt.setTimestamp(1, Timestamp.valueOf(endOfDay));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    taskSchedules.add(mapRowToTaskSchedule(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskSchedules;
    }

    /**
     * 查找开始时间在date1和date2之间（含当天）的任务
     * @param date1
     * @param date2
     * @return
     */
    public static List<TaskSchedule> TaskBetween(LocalDate date1,LocalDate date2){
        List<TaskSchedule> taskSchedules = new ArrayList<>();
        String sql = "SELECT * FROM task_schedule WHERE start_time >= ? AND start_time < ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            LocalDateTime start1 = date1.atStartOfDay();
            LocalDateTime end2 = date2.atStartOfDay().plusDays(1);
            pstmt.setTimestamp(1, Timestamp.valueOf(start1));
            pstmt.setTimestamp(2, Timestamp.valueOf(end2));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    taskSchedules.add(mapRowToTaskSchedule(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskSchedules;
    }

    /**
     * 将结果集的一行映射为TaskSchedule对象
     * @param rs 结果集
     * @return TaskSchedule对象
     */
    private static TaskSchedule mapRowToTaskSchedule(ResultSet rs) throws SQLException {
        TaskSchedule taskSchedule = new TaskSchedule();
        taskSchedule.setId(rs.getInt("id"));
        taskSchedule.setTaskId(rs.getInt("task_id"));
        taskSchedule.setUserId(rs.getInt("user_id"));
        taskSchedule.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
        taskSchedule.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
        return taskSchedule;
    }
}
