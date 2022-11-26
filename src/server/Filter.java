package server;

import DAO.JDBC;
import Utils.SaveUserLogin;
import main.EditBlog;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

@WebFilter(filterName = "Filter")
public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }






    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {


        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String path = (String) request.getServletPath();
        System.out.println("+++++++Filter+++++++"+path+"+++++++Filter+++++++");



        SaveUserLogin saveUserLogin = new SaveUserLogin();
        String cookieUsr = saveUserLogin.loginCookis(request,"blogCookNameUsr");
        String cookiePwd = saveUserLogin.loginCookis(request,"blogCookNamePwd");
        String cookieID = saveUserLogin.loginCookis(request,"blogCookNameID");

        //已经登陆
        if (Objects.equals(cookieUsr+cookiePwd,login(cookieUsr)) && login(cookieUsr).length() > 0){


            if(Objects.equals(path,"/deleBlog.sc")){

                String blogName = (String)request.getParameter("blogName");
                String usrId  = usrAction(blogName);

                if(Objects.equals(usrId,cookieID)){
                    chain.doFilter(req, resp);
                }else {
                    try {

                        JSONObject json = new JSONObject();
                        json.put("result","noself");
                        response.getOutputStream().write(json.toString().getBytes("utf8"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }


            }else {
                chain.doFilter(req, resp);
            }


        }else {
            //没有登陆
            request.getRequestDispatcher("/index.jsp?statu=noself").forward(request,response);
        }

    }

    /**
     * 判断用户是否已经登陆
     * @param usr
     * @return pwd
     */
    public  String login(String usr){
        Connection c  = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        if (usr.length()< 0) return "";

        try {
            c = JDBC.GetConnection();
            String sql = "select * from usr where usr = ?";
            p = c.prepareStatement(sql);
            p.setObject(1,usr);

            resultSet = p.executeQuery();
            while (resultSet.next()){
                String pwd = resultSet.getString("pwd");
                String tmpUsr = resultSet.getString("usr");
                return tmpUsr+pwd;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBC.close(p,c);
        }

        return "";
    }



    /**
     * 判断用户是否在操作自己数据
     * @param blogName
     * @return 博客的用户ID
     */
    public static String usrAction(String blogName){
        Connection c  = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        if (blogName.length()< 0) return "";

        try {
            c = JDBC.GetConnection();
            String sql = "select * from blogName where blogName = ?";
            p = c.prepareStatement(sql);
            p.setObject(1,blogName);

            resultSet = p.executeQuery();
            while (resultSet.next()){
                return resultSet.getString("usrID");
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBC.close(p,c);
        }

        return "";
    }




    public void init(FilterConfig config) throws ServletException {

    }

}
