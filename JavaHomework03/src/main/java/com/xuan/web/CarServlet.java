package com.xuan.web;

import com.sun.xml.internal.rngom.parse.host.Base;
import com.xuan.domain.*;
import com.xuan.service.CarService;
import com.xuan.service.OderService;
import com.xuan.service.ProductService;
import com.xuan.service.impl.CarServiceImpl;
import com.xuan.service.impl.OderServideImpl;
import com.xuan.service.impl.ProductServiceImpl;
import com.xuan.util.MailUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/car/*")
public class CarServlet extends BaseServlet {
    private CarService carService=new CarServiceImpl();
    public void addCar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String username= (String) request.getSession().getAttribute("user");
        String pnostr=request.getParameter("pno");
        int pno=Integer.valueOf(pnostr);
        carService.addCar(username,pno);
        System.out.println(username+"加入购物车"+pno);
    }
    public void subOne(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String username= (String) request.getSession().getAttribute("user");
        String pnostr=request.getParameter("pno");
        int pno=Integer.valueOf(pnostr);
        carService.subOne(username,pno);
        //Service方法已经写好
        System.out.println("减少商品");
    }
    public void delectOne(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String username= (String) request.getSession().getAttribute("user");
        String pnostr=request.getParameter("pno");
        int pno=Integer.valueOf(pnostr);
        carService.delectOne(username,pno);
        System.out.println("删除商品");
    }
    //查看购物车全部内容
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String username= (String) request.getSession().getAttribute("user");
        //得到的即是购物车对象
        CarBean carBean=carService.findAll(username);
        System.out.println(writeValueAsString(carBean));
        writeValue(carBean,response);

    }
    //确认订单
    public void confirmOrder(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // 得到用户名、邮箱和地址
        String username= (String) request.getSession().getAttribute("user");
        String addre=request.getParameter("addre");
        String email=request.getParameter("email");
        System.out.println(username);
        System.out.println(addre);
        System.out.println(email);
        //得到购物车内容并加入邮件
        //购物车对象
        CarBean carBean=carService.findAll(username);
        //购物车内容
        List<CarItem> carItemList=carBean.getCarObject();
        //存储具体商品信息以便加入订单表
        List<OderItem> orderPro=new LinkedList<>();
        //根据商品编号得到商品名称
        ProductService productService=new ProductServiceImpl();
        StringBuilder emailText=new StringBuilder("");
        double sumPrice=0;
        emailText.append("亲爱的："+username+":");
        emailText.append("<br>");
        emailText.append("已经收到您的订单");
        emailText.append("<br>");
        emailText.append("收货地址："+addre);
        emailText.append("<br>");
        emailText.append("订单详情:");
        emailText.append("<br>");
        emailText.append("<table border=\"1\" cellspacing=\"0\">\n    <tr>\n    <td>名称</td>\n    <td>数量</td>\n  <td>单价</td> <td>总价</td> </tr>\n ");
        for (CarItem carItem : carItemList) {
            Product itemProduct=productService.findOne(carItem.getPno());
            String pname=itemProduct.getPname();
            double pprice=itemProduct.getPprice();
            long pnums=carItem.getNums();
            //处理订单项，准备加入订单表
            OderItem tempOder=new OderItem();
            tempOder.setProduct(itemProduct);
            tempOder.setNum((int) pnums);
            orderPro.add(tempOder);

            //System.out.println(pname+"  数量： "+pnums)
            emailText.append("<tr><td>"+pname+"</td><td>"+pnums+"</td><td>"+pprice+"</td>"+"<td>"+pnums*pprice+"</td>");
            sumPrice+=pnums*pprice;
            //删除购物车项目
            carService.delectOne(username,carItem.getPno());
        }
        emailText.append("</table>");
        emailText.append("<h3>总计："+sumPrice+"</h3>");
        String text=emailText.toString();
        System.out.println(text);
        MailUtils.sendMail(email,text,"下单详情");

        //添加进数据库订单管理
        Date da=new Date();
        System.out.println(da);
        SimpleDateFormat SFDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strda=SFDate.format(da);
        /*
        System.out.println("用户名"+username);
        System.out.println("邮箱号"+email);
        System.out.println("地址"+addre);
        System.out.println("内容"+writeValueAsString(orderPro));
        System.out.println("总金额"+sumPrice);
        System.out.println("日期"+strda);*/

        OderService oderService=new OderServideImpl();
        oderService.addOne(username,email,addre,writeValueAsString(orderPro),sumPrice,strda);
    }

    //查看所有订单
    public void findAllOrder(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        OderService oderService=new OderServideImpl();
        List<OrderDetail> orders=oderService.findAll();
        writeValue(orders,response);
    }
}
