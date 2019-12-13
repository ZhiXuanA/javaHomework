package com.xuan.dao.impl;

import com.xuan.dao.FootDao;
import com.xuan.domain.CarItem;
import com.xuan.domain.FootBean;
import com.xuan.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class FootDaoImpl implements FootDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public void addOne(String uname, String pno) {
        String sqlCount="SELECT count(*) FROM tab_foot where uname=? and pno=?";
        int num=template.queryForObject(sqlCount, Integer.class ,uname, pno);
        //System.out.println(num);
        if(num==0){
            String sqlInsert="INSERT INTO `tab_foot`(`uname`, `time`, `pno`) VALUES (?,?,?)";
            template.update(sqlInsert,uname,10,pno);
        }
        else{
            String sqlUpdate="UPDATE `tab_foot` SET time=time+10 where uname=? and pno=?";
            template.update(sqlUpdate,uname,pno);
        }
    }

    @Override
    public List<FootBean> findAll() {
        String sql="SELECT * FROM tab_foot";
        return  template.query(sql, new BeanPropertyRowMapper<FootBean>(FootBean.class));

    }
}
