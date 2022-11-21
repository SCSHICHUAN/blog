package server;

import main.EditBlog;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(filterName = "Filter")
public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        System.out.println("+++++++Filter+++++++");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String path = request.getServletPath();
        System.out.println("requst path =" + path);



        if(Objects.equals(path,"/edit")){
            reDirect(request,response);
        }else {
            chain.doFilter(req, resp);
        }


    }


    public static void reDirect(HttpServletRequest request, HttpServletResponse response) throws IOException{

        //获取当前请求的路径
        String basePath = request.getScheme() +"://" + request.getServerName() + ":" + request.getServerPort()+request.getContextPath();
        //如果request.getHeader("X-Requested-With") 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            //告诉ajax我是重定向
            response.setHeader("REDIRECT","REDIRECT");
            //告诉ajax我重定向的路径
            response.setHeader("CONTENTPATH", basePath+"/?name=stan");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }


    public void init(FilterConfig config) throws ServletException {

    }

}
