package com.todolist.dao;

import com.todolist.config.UserSession;
import com.todolist.entity.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagDao {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/database/todolist.db"; // 数据库文件的路径

    /**
     * 插入tag并返回id
     * @param tag
     * @return
     */
    public static int insert(Tag tag){
        String sql = "INSERT INTO tag (name, description, color_code, user_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, tag.getName());
            pstmt.setString(2, tag.getDescription());
            pstmt.setInt(3, tag.getColorCode());
            pstmt.setInt(4, UserSession.getInstance().getCurrentUser().getId());

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
     * 根据用户ID查找所有标签
     *
     * @return 标签列表
     */
    public static List<Tag> selectTagsByUserId() {
        int userId = UserSession.getInstance().getCurrentUser().getId();
        List<Tag> tags = new ArrayList<>();
        String sql = "SELECT * FROM tag WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tags.add(mapRowToTag(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    /**
     * 将结果集的一行映射为Tag对象
     * @param rs 结果集
     * @return Tag对象
     */
    private static Tag mapRowToTag(ResultSet rs) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getInt("id"));
        tag.setName(rs.getString("name"));
        tag.setDescription(rs.getString("description"));
        tag.setColorCode(rs.getInt("color_code"));
        tag.setUser_id(rs.getInt("user_id"));
        return tag;
    }

    
}
