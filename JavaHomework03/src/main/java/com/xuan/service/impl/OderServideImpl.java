package com.xuan.service.impl;

import com.xuan.dao.OderDao;
import com.xuan.dao.impl.OderDaoImpl;
import com.xuan.domain.OrderDetail;
import com.xuan.service.OderService;

import java.util.List;

public class OderServideImpl implements OderService {
    OderDao oderDao=new OderDaoImpl();
    @Override
    public void addOne(String uname, String uemain, String addre, String car, double sum, String date) {
        oderDao.addOne(uname, uemain, addre, car, sum, date);
    }

    @Override
    public List<OrderDetail> findAll() {
        return oderDao.findAll();
    }

}
