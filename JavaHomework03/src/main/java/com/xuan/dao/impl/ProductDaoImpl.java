package com.xuan.dao.impl;

import com.xuan.dao.ProductDao;
import com.xuan.domain.Product;
import com.xuan.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Product> findAll(int start, int pageSize, String pname) {
        String sql="select * from tab_product where 1=1 ";
        StringBuilder sb=new StringBuilder(sql);
        List params = new ArrayList();//条件们

        if(pname!=null &&pname.length()>0){
            sb.append("and pname like ?");
            params.add("%"+pname+"%");
        }
        //分页条件
        sb.append(" limit ? , ? ");//分页条件
        params.add(start);
        params.add(pageSize);

        sql=sb.toString();
        //System.out.println("ProductDao");
        //System.out.println(sql);
        //System.out.println(params);
        return template.query(sql,new BeanPropertyRowMapper<Product>(Product.class),params.toArray());
    }

    @Override
    public int findTotal(String pname) {
        String sql="select count(*) from tab_product where 1=1 ";
        StringBuilder sb=new StringBuilder(sql);
        List params = new ArrayList();//条件们

        if(pname!=null &&pname.length()>0){
            sb.append("and pname like ?");
            params.add("%"+pname+"%");
        }

        sql=sb.toString();
       // System.out.println("ProductDao");
        //System.out.println(sql);
        //System.out.println(params);
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public int addOne(String pname, String pnote, double pprice) {
        String sql="insert into tab_product values (null,?,?,?) ";
        template.update(sql,pname,pnote,pprice);
        sql="SELECT LAST_INSERT_ID();";
        int lastID=template.queryForObject(sql,Integer.class);
        //System.out.println(lastID);
        return lastID;
    }

    @Override
    public Product findOne(int pno) {
        String sql="select * from tab_product where pno=?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Product>(Product.class),pno);
    }

    @Override
    public void delectOne(int pno) {
        String sql="DELETE FROM `tab_product` WHERE pno=?";
        template.update(sql,pno);
        return;
    }

    @Override
    public void updateOne(String pname,String pnote,double pprice,int pno) {
        String sql="UPDATE `tab_product` SET `pname`=?,`pnote`=?,`pprice`=? WHERE pno=?";
        template.update(sql,pname,pnote,pprice,pno);
    }
}
