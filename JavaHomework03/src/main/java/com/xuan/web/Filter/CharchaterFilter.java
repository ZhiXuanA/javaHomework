package com.xuan.web.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决全站乱码问题，处理所有的请求
 */
@WebFilter("/*")
public class CharchaterFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain filterChain) throws IOException, ServletException {

        //将父接口转为子接口
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;

        String path=request.getRequestURI();

        if(path.contains(".css") || path.contains(".js") || path.contains(".png")|| path.contains(".jpg")){
            //如果发现是css或者js文件，直接放行
            //System.out.println("不过滤"+path);
            filterChain.doFilter(request, response);
            return;
        }
        //获取请求方法
       // System.out.println("过滤"+path);
        String method = request.getMethod();
        //解决post请求中文数据乱码问题
        if(method.equalsIgnoreCase("post")){
            request.setCharacterEncoding("utf-8");
            //处理响应乱码
            response.setContentType("text/html;charset=utf-8");
            //System.out.println("执行完");

        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
