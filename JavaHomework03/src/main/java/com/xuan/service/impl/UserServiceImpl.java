package com.xuan.service.impl;

import com.xuan.dao.UserDao;
import com.xuan.dao.impl.UserDaoImpl;
import com.xuan.domain.User;
import com.xuan.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao=new UserDaoImpl();
    @Override
    public boolean regist(User user) {
        //进行注册并根据结果返回true或者false
        User u=userDao.findUser(user.getUsername());
        if(u!=null)
        {
            return false;
        }
        else {
            userDao.addUser(user);
            return true;
        }
    }

    //用户登录功能
    @Override
    public User login(User user) {
        User u=userDao.loginUer(user.getUsername(),user.getPassword());
        return u;
    }


}
