package com.xuan.dao;

import com.xuan.domain.FootBean;

import java.util.List;

public interface FootDao {
   void addOne(String uname,String pno);
   List<FootBean> findAll();
}
