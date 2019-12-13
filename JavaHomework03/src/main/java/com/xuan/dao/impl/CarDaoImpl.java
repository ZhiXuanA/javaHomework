package com.xuan.dao.impl;

import com.xuan.dao.CarDao;
import com.xuan.domain.CarItem;
import com.xuan.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CarDaoImpl implements CarDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public void addCar(String username, int pno) {
        String sql="INSERT INTO tab_car VALUES (?,?)";
        template.update(sql,username,pno);

    }

    @Override
    public void subOne(String username, int pno) {
        String sql="DELETE FROM tab_car WHERE username=? and pno=? limit 1";
        template.update(sql,username,pno);
    }


    @Override
    public void delectOne(String username, int pno) {
        String sql="DELETE FROM tab_car WHERE username=? and pno=?";
        template.update(sql,username,pno);
    }

    @Override
    public void delectOnePro(int pno) {
        String sql="DELETE FROM tab_car WHERE pno=?";
        template.update(sql,pno);
    }

    @Override
    public List<CarItem> findAll(String username) {
        String sql="SELECT pno,count(*) nums from tab_car where username=? group by pno";
        return template.query(sql, new BeanPropertyRowMapper<CarItem>(CarItem.class),username);
    }
}
