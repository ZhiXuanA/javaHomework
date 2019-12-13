package com.xuan.web.Filter;


import com.xuan.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//在这里写需要过滤的页面，前提是这个页面有用户
//@WebFilter({"/login.html","/login_ok.html"})
@WebFilter({"/login.html","/login_ok.html","/car.html","/orderform.html"})
public class AutoLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;
        Cookie[] cookies=req.getCookies();
        String autoLogin=null;
        String username=null;
        if(cookies!=null)
        for(int i=0;i<cookies.length;i++)
        {
            if("autoLogin".equals(cookies[i].getName()))
            {
                //System.out.println("得到了cookie0---");
                autoLogin="1";
            }
            if("user".equals(cookies[i].getName())){
                //System.out.println("得到了cookie1---");
                username=cookies[i].getValue();
            }
            if(autoLogin!=null&&username!=null)
                break;
        }

        //设置了自动登录且设置成功
        if(autoLogin!=null&&username!=null) {
            //System.out.println("拦截到Session" + user);
            req.getSession().setAttribute("user",username);
            System.out.println("设置用户session,进行重定向");
            if(req.getRequestURI().equals(req.getContextPath()+"/login.html"))
                resp.sendRedirect(req.getContextPath()+"/login_ok.html");
            //req.getRequestDispatcher("/login_ok.html").forward(req,resp);
            filterChain.doFilter(req,servletResponse);
            //return;
        }
        //处于登录状态
        else if(req.getSession().getAttribute("user")!=null){
            String user=(String)req.getSession().getAttribute("user");
            if(req.getRequestURI().equals(req.getContextPath()+"/login.html"))
                resp.sendRedirect(req.getContextPath()+"/login_ok.html");
            System.out.println("说明更新了");
            System.out.println("重定向到登录成功页面");
            filterChain.doFilter(req,servletResponse);

        }
        //没有得到cookie需要重定向
        else {
            if(!req.getRequestURI().equals(req.getContextPath()+"/login.html"))
                resp.sendRedirect(req.getContextPath()+"/login.html");
            System.out.println("重定向到登录页面");
            filterChain.doFilter(req,servletResponse);
        }


    }

    @Override
    public void destroy() {

    }
}
