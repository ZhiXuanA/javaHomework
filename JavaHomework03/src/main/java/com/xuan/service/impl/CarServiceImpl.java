package com.xuan.service.impl;

import com.xuan.dao.CarDao;
import com.xuan.dao.impl.CarDaoImpl;
import com.xuan.domain.CarBean;
import com.xuan.domain.CarItem;
import com.xuan.service.CarService;

import java.util.List;

public class CarServiceImpl implements CarService {
    CarDao carDao=new CarDaoImpl();
    @Override
    public void addCar(String username, int pno) {
        carDao.addCar(username,pno);
    }

    @Override
    public void subOne(String username, int pno) {
        carDao.subOne(username,pno);
    }


    @Override
    public void delectOne(String username, int pno) {
        carDao.delectOne(username,pno);
    }

    @Override
    public CarBean findAll(String username) {
        List<CarItem> carItems=carDao.findAll(username);
        CarBean carBean=new CarBean();
        carBean.setCarObject(carItems);
        return carBean;

    }
}
