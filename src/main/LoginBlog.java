package main;

import DAO.JDBC;
import Utils.SaveUserLogin;
import model.Usr;

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
        Usr usr1 = login(usr);
        if (Objects.equals(pwd,usr1.pwd)){
            SaveUserLogin saveUserLogin = new SaveUserLogin();
            saveUserLogin.save(response,usr,pwd,usr1.usrID);
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
    public  Usr login(String usr){
        Connection c  = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        Usr usr1 = new Usr("","","","");

        try {
            c = JDBC.GetConnection();
            String sql = "select * from usr where usr = ?";
            p = c.prepareStatement(sql);
            p.setObject(1,usr);

            resultSet = p.executeQuery();
            while (resultSet.next()){
                String pwd = resultSet.getString("pwd");
                String usrID = resultSet.getString("usrID");

                usr1 = new Usr(usrID,"",pwd,"");
                System.out.print(usr1);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBC.close(p,c);
        }

        return usr1;

    }



}
