package com.xuan.service.impl;

import com.xuan.dao.FootDao;
import com.xuan.dao.impl.FootDaoImpl;
import com.xuan.domain.FootBean;
import com.xuan.service.FootService;

import java.util.List;

public class FootServiceImpl implements FootService {
    FootDao footDao=new FootDaoImpl();
    @Override
    public void addOne(String uname, String pno) {
        footDao.addOne(uname,pno);
    }

    @Override
    public List<FootBean> findAll() {
        return footDao.findAll();
    }
}
