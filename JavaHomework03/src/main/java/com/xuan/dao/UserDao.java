package com.xuan.dao;

import com.xuan.domain.User;

public interface UserDao {
    //查看用户名是否存在
    User findUser(String username);
    void addUser(User user);
    //用户登录功能
    User loginUer(String username,String password);

}
