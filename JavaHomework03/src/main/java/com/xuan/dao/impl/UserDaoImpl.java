package com.xuan.dao.impl;

import com.xuan.dao.UserDao;
import com.xuan.domain.User;
import com.xuan.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    //查看用户名是否存在
    public User findUser(String username) {
        User user=null;
        try {
            String sql="SELECT * FROM `tab_user` WHERE username=?";
            user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username);
        }
        catch (Exception e){
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        String sql="INSERT INTO `tab_user` (`username`, `password`, `name`, `telephone`,  `sex`,`birthday`) VALUES(?,?,?,?,?,?)";
        template.update(sql,user.getUsername(),
                            user.getPassword(),
                            user.getName(),
                            user.getTelephone(),
                            user.getSex(),
                            user.getBirthday());
    }

    @Override
    public User loginUer(String username, String password) {
        User u=null;
        try{
            String sql="SELECT * FROM `tab_user` WHERE username=? and password=?";
            u=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
            System.out.println("查询到了");
        }catch (Exception e)
        {
            System.out.println("没查到");
        }
        return u;
    }
}
