package com.todolist.dao;

import com.todolist.entity.Task;
import com.todolist.entity.TaskTag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskTagDao {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/database/todolist.db"; // 数据库文件的路径

    /**
     * 插入
     * @param taskTag
     */
    public static void insert(TaskTag taskTag){
        String sql = "INSERT INTO task_tag (task_id, tag_id, user_id) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskTag.getTask_id());
            pstmt.setInt(2, taskTag.getTag_id());
            pstmt.setInt(3, taskTag.getUser_id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找tag对应的task
     * @param userId
     * @param tagId
     * @return
     */
    public static List<Integer> selectTaskId(int userId,int tagId){
        List<Integer> taskIds = new ArrayList<>();
        String sql = "SELECT task_id FROM task_tag WHERE user_id = ? AND tag_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, tagId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    taskIds.add(rs.getInt("task_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskIds;
    }

    /**
     * 查找task对应的tag
     * @param userId
     * @param taskId
     * @return
     */
    public static List<Integer> selectTagId(int userId,int taskId){
        List<Integer> tagIds = new ArrayList<>();
        String sql = "SELECT tag_id FROM task_tag WHERE user_id = ? AND task_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, taskId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tagIds.add(rs.getInt("tag_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tagIds;
    }

}
