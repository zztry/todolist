package com.todolist;

import com.todolist.config.SQLiteConfig;
import com.todolist.gui.MainFrame;

import javax.swing.*;
import java.sql.SQLException;


public class Application {
    public static void main(String[] args) throws SQLException {

        //首次启动时使用initial初始化数据库（创建数据库表）
        //本项目把数据库文件放在./src/main/resources/database下，驱动由maven管理，故本地没有数据库也不需要初始化且不需要本地下载jdbc驱动
        //SQLiteConfig sqLiteConfig = new SQLiteConfig();
        //sqLiteConfig.initialSQLite();

        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
