package com.xuan.web;

import com.xuan.domain.FootBean;
import com.xuan.service.FootService;
import com.xuan.service.impl.FootServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/foot/*")
public class FootServlet extends BaseServlet {
    FootService footService=new FootServiceImpl();
    public void addOne(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String uname=request.getParameter("uname");
        String pnostr=request.getParameter("pno");
        System.out.println(uname+"足迹"+pnostr);
        footService.addOne(uname,pnostr);
    }
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException{
        List<FootBean> foots=footService.findAll();

        System.out.println(writeValueAsString(foots));
        writeValue(foots,response);
    }

}
