package com.xuan.dao;

import com.xuan.domain.Product;

import java.util.List;

public interface ProductDao {

    List<Product> findAll(int start,int pageSize,String pname);
    int findTotal(String pname);

    int addOne(String pname,String pnote,double pprice);

    Product findOne(int pno);

    void delectOne(int pno);

    void updateOne(String pname,String pnote,double pprice,int pno);
}
