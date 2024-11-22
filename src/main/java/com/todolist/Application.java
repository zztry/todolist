package com.todolist;

import com.todolist.config.SQLiteConfig;
import com.todolist.controller.TagController;
import com.todolist.controller.TaskController;
import com.todolist.controller.UserController;
import com.todolist.entity.Tag;
import com.todolist.entity.Task;
import com.todolist.entity.User;
import com.todolist.gui.MainFrame;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Application {
    public static void main(String[] args) throws SQLException {

        //首次启动时使用initial初始化数据库（创建数据库/表），初始化表脚本在src/main/resources/initialdb.sql
        //本项目把数据库文件放在./src/main/resources/database下，驱动由maven管理，故本地没有数据库也不需要初始化且不需要本地下载jdbc驱动
        //SQLiteConfig sqLiteConfig = new SQLiteConfig();
        //sqLiteConfig.initialSQLite();

        //图形化界面 未完成
        //SwingUtilities.invokeLater(() -> new MainFrame());

        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("请登录");
            boolean canLogin = false;
            while(!canLogin){
                System.out.println("请输入用户名：");
                String username = scanner.nextLine();
                System.out.println("请输入密码：");
                String password = scanner.nextLine();
                canLogin = UserController.userLogin(username, password);
                if(!canLogin){
                    System.out.println("登陆失败");
                }
            }


            System.out.println("登录成功");
            while(true){
                System.out.println("选择功能");
                System.out.println("1.查看今日任务 2.新增任务 3.查看tag 4.增加tag");
                int func = scanner.nextInt();
                scanner.nextLine();

                if(func==1){
                    List<Task> tasks = TaskController.selectTodayTask();
                    for (Task task : tasks) {
                        System.out.println(task.toString2());
                    }
                }
                else if(func==2){
                    System.out.println("请输入任务并选择tag");

                }
                else if(func == 3){
                    List<Tag> list = TagController.list();
                    System.out.println("该用户tag有");
                    for (Tag tag : list) {
                        System.out.println(tag.toString2());
                    }
                }
                else if(func == 4){
                    System.out.println("新增tag 请输入tagName和tagDescription");
                    System.out.println("tagName:");
                    String tagname = scanner.nextLine();

                    System.out.println("tagDescription:");
                    String tagdescription = scanner.nextLine();
                    int tagId = TagController.addTag(tagname, tagdescription);
                    if(tagId>0){
                        System.out.println("插入成功");
                    }
                }
            }



        }
    }
}
