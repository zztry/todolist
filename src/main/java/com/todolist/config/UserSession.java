package com.todolist.config;

import com.todolist.entity.User;

//单例模式构造用户session类，获取当前已登录的用户信息
public class UserSession {

    private static UserSession instance;
    private boolean loggedIn;
    private User currentUser;

    // 私有构造函数，防止实例化
    private UserSession() {}



    // 获取单例实例
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // 设置当前用户
    public void setCurrentUser(User user) {
        this.currentUser = user;
        this.loggedIn = true;
    }

    // 获取当前用户
    public User getCurrentUser() {
        return currentUser;
    }

    // 退出当前用户
    public void logout() {
        this.currentUser = null;
        this.loggedIn = false;
    }

    //获取登录状态
    public boolean isLoggedIn() {
        return this.loggedIn;
    }
}
