package com.todolist.config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConfig {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/database/todolist.db"; // 数据库文件的路径

    static {
        // 静态块用于加载SQLite JDBC驱动
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //Connection conn = null;
    //Statement stmt = null;

    public void initialSQLite() throws SQLException {
        // 创建数据库连接
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            // 创建Statement对象
            try (Statement statement = connection.createStatement()) {
                // 创建表的SQL语句
                String createUserTable = "CREATE TABLE IF NOT EXISTS user (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "username TEXT NOT NULL, " +
                        "password TEXT NOT NULL, " +
                        "email TEXT NOT NULL, " +
                        "phone TEXT, " +
                        "is_login INTEGER DEFAULT 0, " +
                        "deadlineReminderDays INTEGER);";

                // 执行创建表的SQL语句
                statement.execute(createUserTable);
                //完整sql脚本在src/main/resources/initialdb.sql


                System.out.println("创建user表");
            }
        }
    }

}

