package com.xuan.dao.impl;

import com.xuan.dao.OderDao;
import com.xuan.domain.OrderDetail;
import com.xuan.domain.Product;
import com.xuan.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class OderDaoImpl implements OderDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public void addOne(String uname, String uemain, String addre, String car, double sum, String date) {
        String sql="INSERT INTO `tab_order` (`uname`, `uemail`, `addre`, `car`, `sum`, `date`) VALUES (?,?,?,?,?,?)";
        template.update(sql,uname,uemain,addre,car,sum,date);
    }

    @Override
    public List<OrderDetail> findAll() {
        String sql="SELECT * FROM tab_order";
        return template.query(sql,new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
    }
}
