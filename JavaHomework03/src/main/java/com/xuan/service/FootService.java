package com.xuan.service;

import com.xuan.domain.FootBean;

import java.util.List;

public interface FootService {
    void addOne(String uname, String pno);
    List<FootBean> findAll();
}
