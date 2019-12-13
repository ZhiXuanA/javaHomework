package com.xuan.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xuan.domain.PageBean;
import com.xuan.domain.Product;
import com.xuan.service.ProductService;
import com.xuan.service.impl.ProductServiceImpl;
import com.xuan.web.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet("/product/*")
public class ProductServlet extends BaseServlet {
    private ProductService pdService=new ProductServiceImpl();
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        //接受rname
        String pname = request.getParameter("pname");
        if(!(pname!=null&&pname!="null"&&pname.length()>0))
            pname="";
        if(pname==null||pname.equals("null")||pname.length()<=0)
            pname="";
//        System.out.println(pname.equals("null"));
//        System.out.println(pname.getClass().toString());
//        System.out.println("获得的Pname是---"+pname+"----页码是"+currentPageStr);
        int currentPage = 0;//当前页码，如果不传递，则默认为第一页
        if(currentPageStr != null && currentPageStr.length() >0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }

        int pageSize = 0;//每页显示条数，如果不传递，默认每页显示5条记录
        if(pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }

        PageBean<Product> pb = pdService.pageQuery(currentPage, pageSize,pname);
        writeValue(pb,response);
    }
    public void findOne (HttpServletRequest request, HttpServletResponse response) throws IOException{
        String pnostr = request.getParameter("pno");
        System.out.println(pnostr);
        int pno=Integer.valueOf(pnostr);
        Product reProduct=pdService.findOne(pno);
        writeValue(reProduct,response);
    }
    public  void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ProductService productService=new ProductServiceImpl();
        List<Product> products=productService.findAll();
        writeValue(products,response);
        //System.out.println(writeValueAsString(products));
    }
    //商家删除一种商品
    public void deleteOnePro(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ProductService productService=new ProductServiceImpl();
        String pnostr = request.getParameter("pno");
        int pno=Integer.valueOf(pnostr);
        productService.deleteOnePro(pno);
    }

}
