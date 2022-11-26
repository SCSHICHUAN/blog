package server;

import DAO.JDBC;
import Utils.SaveUserLogin;
import main.EditBlog;

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
        String path = request.getServletPath();
        System.out.println("+++++++Filter+++++++"+path);


        SaveUserLogin saveUserLogin = new SaveUserLogin();
        String cookieUsr = saveUserLogin.loginCookis(request,"blogCookNameUsr");
        String cookiePwd = saveUserLogin.loginCookis(request,"blogCookNamePwd");

        //已经登陆
        if (Objects.equals(cookieUsr+cookiePwd,login(cookieUsr)) && login(cookieUsr).length() > 0){
            chain.doFilter(req, resp);
        }else {//非法请求
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        }

    }

    /**
     *
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
                System.out.print(tmpUsr+pwd);
                return tmpUsr+pwd;
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
