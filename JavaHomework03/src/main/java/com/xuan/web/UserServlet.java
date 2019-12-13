package com.xuan.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuan.domain.ResultInfo;
import com.xuan.domain.User;
import com.xuan.service.UserService;
import com.xuan.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet{
    private UserService userService=new UserServiceImpl();
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //验证校验
        String check = request.getParameter("check");
        //从sesion中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次

        //比较Session中的验证码和输入的验证码
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            System.out.println("验证码错误");
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }



        Map<String,String[]> map=request.getParameterMap();
        User user=new User();
        //把请求数据封装成USER对象
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //查询USER登录
        User u=userService.login(user);
        System.out.println(user);
        ResultInfo resultInfo=new ResultInfo();
        if(u==null)
        {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误");
        }
        else {
            System.out.println("登录成功");
            request.getSession().setAttribute("user",u.getUsername());
            resultInfo.setFlag(true);
            //设置自动登录的cookie
            String autoLogin=(String)request.getParameter("autoLogin");
            //1表示自动登录，0表示不自动登录
            //System.out.println(autoLogin);
            if("on".equals(autoLogin)) {
//                request.getSession().setAttribute("user", u);
//                request.getSession().setAttribute("autoLogin", "1");
//                request.getSession().setMaxInactiveInterval(30*60);
                Cookie cookie=new Cookie("autoLogin","1");
                cookie.setMaxAge(30*60);
                cookie.setPath(request.getContextPath());
                Cookie cookie1=new Cookie("user",u.getUsername());
                cookie1.setMaxAge(30*60);
                cookie1.setPath(request.getContextPath());
                response.addCookie(cookie);
                response.addCookie(cookie1);
                //System.out.println("设置了自动登录");
            }
        }

        //封装为json
        ObjectMapper mapper=new ObjectMapper();
        response.setContentType("applicattion/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),resultInfo);
    }
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("autoLogin");
        Cookie cookie=new Cookie("autoLogin","0");
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        Cookie cookie1=new Cookie("user","msg");
        cookie1.setMaxAge(0);
        cookie1.setPath(request.getContextPath());
        response.addCookie(cookie);
        response.addCookie(cookie1);
        request.getSession().removeAttribute("user");
        System.out.println("移除了Cookie和Session");
        System.out.println(request.getContextPath()+"/login.html");
        response.sendRedirect(request.getContextPath()+"/login.html");
        //request.getRequestDispatcher("/login.html").forward(request,response);
        return;
    }
    public void regist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //验证校验
        String check = request.getParameter("check");
        //从sesion中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次

        //比较Session中的验证码和输入的验证码
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            System.out.println("验证码错误");
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //使用BeanUtils封装对象
        Map<String, String[]> map = request.getParameterMap();
        System.out.println(request.getCharacterEncoding());
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //System.out.println(user);

        //实现注册功能
        boolean flag=userService.regist(user);

        //根据flag判断注册是否成功
        ResultInfo info = new ResultInfo();
        if(flag) {
            info.setFlag(true);
        }
        else {
            info.setFlag(false);
            info.setErrorMsg("注册失败,用户名已存在");
        }
        //转换为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
    public void findOne(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String user=null;
        try {
            user=(String)request.getSession().getAttribute("user");
        }catch (Exception e)
        {

        }
        if(user!=null) {
            //System.out.println(user + "是用户！！！");
            response.setCharacterEncoding("utf8");
            ResultInfo resultInfo=new ResultInfo();
            resultInfo.setFlag(true);
            resultInfo.setData(user);
            //封装为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(resultInfo);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
          //  System.out.println("哈哈哈-----------哈哈哈");
          //  System.out.println(json);
        }
        else {
            user="请登录";
        }
    }
}
