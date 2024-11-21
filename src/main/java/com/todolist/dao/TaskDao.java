package com.todolist.dao;

import com.todolist.entity.Task;

import java.sql.*;

public class TaskDao {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/database/todolist.db"; // 数据库文件的路径

    /**
     * 插入一个task
     * @param task
     * @return task_id
     */
    public static int insert(Task task){
        String sql = "INSERT INTO task (title, description, start_date, end_date, start_time, end_time, priority, repeat_type, repeat_interval, repeat_num, status, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setDate(3, task.getStartDate() != null ? java.sql.Date.valueOf(task.getStartDate()) : null);
            pstmt.setDate(4, task.getEndDate() != null ? java.sql.Date.valueOf(task.getEndDate()) : null);
            pstmt.setTime(5, task.getStartTime() != null ? java.sql.Time.valueOf(task.getStartTime()) : null);
            pstmt.setTime(6, task.getEndTime() != null ? java.sql.Time.valueOf(task.getEndTime()) : null);
            pstmt.setInt(7, task.getPriority());
            pstmt.setInt(8, task.getRepeatType());
            pstmt.setInt(9, task.getRepeatInterval());
            pstmt.setInt(10, task.getRepeatNum());
            pstmt.setInt(11, task.getStatus());
            pstmt.setInt(12, task.getUserId());

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
     * 根据name查找并返回id
     * @param tName
     * @return
     */
    public static int getIdByName(String tName) {
        String sql = "SELECT id FROM task WHERE title = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no ID is found
    }

    /**
     * 根据name查找task
     */
    public static Task getTaskByName(String name){
        String sql = "SELECT * FROM task WHERE title = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToTask(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no task is found
    }

    /**
     * 根据任务ID查找任务
     * @param taskId 任务ID
     * @return 任务对象
     */
    public static Task getTaskById(int taskId) {
        String sql = "SELECT * FROM task WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToTask(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no task is found
    }


    /**
     * 将结果集的一行映射为Task对象
     * @param rs 结果集
     * @return Task对象
     */
    private static Task mapRowToTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setStartDate(rs.getDate("start_date").toLocalDate());
        task.setEndDate(rs.getDate("end_date").toLocalDate());
        task.setStartTime(rs.getTime("start_time").toLocalTime());
        task.setEndTime(rs.getTime("end_time").toLocalTime());
        task.setPriority(rs.getInt("priority"));
        task.setRepeatType(rs.getInt("repeat_type"));
        task.setRepeatInterval(rs.getInt("repeat_interval"));
        task.setRepeatNum(rs.getInt("repeat_num"));
        task.setStatus(rs.getInt("status"));
        task.setUserId(rs.getInt("user_id"));
        return task;
    }

}
