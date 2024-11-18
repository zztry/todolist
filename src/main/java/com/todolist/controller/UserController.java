package com.todolist.controller;


import com.todolist.config.UserSession;
import com.todolist.dao.UserDao;
import com.todolist.entity.User;

public class UserController {

    /**
     * 用户登录
     * @param username
     * @param password
     * @return 登录成功返回true
     */
    public static boolean userLogin(String username,String password){
        User user = UserDao.login(username,password);
        //不为空，登录成功
        if(user != null){
            //设置UserSession ，设置登录状态
            UserSession.getInstance().setCurrentUser(user);
            return true;

        }
        return false;
    }

}
