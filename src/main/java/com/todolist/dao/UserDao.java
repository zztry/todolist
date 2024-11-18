package com.todolist.dao;

import com.todolist.entity.User;
import org.apache.commons.beanutils.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;


public class UserDao {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/database/todolist.db"; // 数据库文件的路径

    public static User login(String username, String password) {


        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?")) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();



            if (rs.next()) { // 如果结果集有数据
                User user = new User(rs.getInt("id"),rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("is_login"),
                        rs.getInt("deadlineReminderDays"));
                return user;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
