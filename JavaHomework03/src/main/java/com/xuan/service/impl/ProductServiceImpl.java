package com.xuan.service.impl;

import com.xuan.dao.CarDao;
import com.xuan.dao.ProductDao;
import com.xuan.dao.impl.CarDaoImpl;
import com.xuan.dao.impl.ProductDaoImpl;
import com.xuan.domain.PageBean;
import com.xuan.domain.Product;
import com.xuan.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao=new ProductDaoImpl();
    @Override
    public PageBean<Product> pageQuery(int currentPage, int pageSize, String pname) {
        PageBean<Product> pageBean=new PageBean<>();
        int start=(currentPage-1)*pageSize;
        List<Product> products=productDao.findAll(start,pageSize,pname);
//        for (Product product : products) {
//            System.out.println(product);
//        }
        int total=productDao.findTotal(pname);
        pageBean.setList(products);
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCount(total);
        int totalPage=(total%pageSize==0)?(total/pageSize):(total/pageSize)+1;
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    @Override
    public int addOne(String pname, String pnote, double pprice) {
        return productDao.addOne(pname,pnote,pprice);
    }

    @Override
    public Product findOne(int pno) {
        return productDao.findOne(pno);
    }

    @Override
    public List<Product> findAll() {
        List<Product> products=productDao.findAll(0,1000,"");
        return products;
    }

    @Override
    public void deleteOnePro(int pno) {
        CarDao carDao=new CarDaoImpl();
        carDao.delectOnePro(pno);
        productDao.delectOne(pno);
    }

    @Override
    public void updateOne(String pname, String pnote, double pprice, int pno) {
        Product old=productDao.findOne(pno);
        String newname=old.getPname();
        String newnote=old.getPnote();
        double newprice=old.getPprice();
        if(pname!=null&&!pname.isEmpty())
            newname=pname;
        if(pnote!=null&&!pnote.isEmpty())
            newnote=pnote;
        if(pprice!=-1)
            newprice=pprice;
        productDao.updateOne(newname,newnote,newprice,pno);
    }
}
