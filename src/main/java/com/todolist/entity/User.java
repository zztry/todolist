package com.todolist.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id; // 主键id
    private String username; // 用户名
    private String password; // 密码
    private String email; // 邮箱
    private String phone; // 电话号码
    private int isLogin; // 登录状态 0未登录 1已登录
    private int deadlineReminderDays; // 截止日期提醒天数，默认为3天
}
