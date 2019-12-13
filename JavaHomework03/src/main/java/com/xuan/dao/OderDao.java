package com.xuan.dao;

import com.xuan.domain.OrderDetail;

import java.util.List;

public interface OderDao {
    void addOne(String uname,String uemain,String addre,String car,double sum,String date);
    List<OrderDetail> findAll();
}
