package main;

import DAO.JDBC;
import Utils.SaveUserLogin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class LoginBlog {

    public void login(HttpServletRequest request, HttpServletResponse response){

        String usr = (String)request.getParameter("usr");
        String pwd = (String)request.getParameter("pwd");

        String result = "";
        if (Objects.equals(pwd,login(usr))){
            SaveUserLogin saveUserLogin = new SaveUserLogin();
            saveUserLogin.save(response,usr,pwd);
            result = "success";

        }else {
            result = "failed";
        }

        try {
            response.getOutputStream().write(result.getBytes("utf8"));
        }catch (Exception e){
            e.printStackTrace();
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

        try {
            c = JDBC.GetConnection();
            String sql = "select * from usr where usr = ?";
            p = c.prepareStatement(sql);
            p.setObject(1,usr);

            resultSet = p.executeQuery();
            while (resultSet.next()){
                String pwd = resultSet.getString("pwd");
                System.out.print(pwd);
                return pwd;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBC.close(p,c);
        }

        return "";
    }



}
