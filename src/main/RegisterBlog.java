package main;

import DAO.JDBC;
import Utils.MailServer;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Random;

import Utils.SaveUserLogin;
import model.Usr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class RegisterBlog {

    public void sendMaliCode(HttpServletRequest request, HttpServletResponse response){


        String mail = (String)request.getParameter("mail");

        //1:定义以字符串的拼接的StringBuilder
        StringBuilder builder = new StringBuilder();
        //准备产生4个字符串的随机数
        for(int i=0;i<4;i++){
            builder.append(randomChar());
        }
        String code = builder.toString();
        System.out.print("+++code++<"+code+">++++++");

        MailServer mailServer = new MailServer(mail,code,response);
        mailServer.run();

        request.getSession().setAttribute("code",code);

    }


    public void registerBlog(HttpServletRequest request, HttpServletResponse response){




        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        String tmpID = sdf.format(now);




        String mail = (String)request.getParameter("mail");
        String mailCode = (String)request.getParameter("mailCode");
        String usrName = (String)request.getParameter("usrName");
        String pwd = (String)request.getParameter("pwd");
        String usrID = tmpID;


        String relust = "";
        String locatCode = (String) request.getSession().getAttribute("code");
        if (Objects.equals(mailCode,locatCode)){


            relust = "success";
             //保存登陆状态
            SaveUserLogin saveUserLogin = new SaveUserLogin();


            Usr oldUsr = haveUsr(mail);
            if(Objects.equals(mail,oldUsr.mail)){
                Usr usr = new Usr(oldUsr.usrID,usrName,pwd,oldUsr.mail);
                saveUserLogin.save(response,usrName,pwd,oldUsr.usrID);
                updateUsr(usr);
            }else {
                Usr usr = new Usr(usrID,usrName,pwd,mail);
                saveUserLogin.save(response,usrName,pwd,usrID);
                addUsr(usr);
            }


        }else {
            relust = "Faile";
        }


        try {
            response.getOutputStream().write(relust.getBytes("utf8"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     *
     * @param usr
     * @return 数据库操作成功
     */
    public  boolean addUsr(Usr usr){
        Connection c  = null;
        PreparedStatement p = null;

        try {
            c = JDBC.GetConnection();
            String sql = "insert usr(usrID,usr,pwd,mail) values(?,?,?,?)";
            p = c.prepareStatement(sql);
            p.setObject(1,usr.usrID);
            p.setObject(2,usr.usr);
            p.setObject(3,usr.pwd);
            p.setObject(4,usr.mail);

            int rows = p.executeUpdate();
            if (rows > 0){
                return true;
            }else {
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            JDBC.close(p,c);
        }
    }


    /**
     * 跟新用户信息
     * @param usr
     * @return
     */
    public  boolean updateUsr(Usr usr){
        Connection c  = null;
        PreparedStatement p = null;

        try {
            c = JDBC.GetConnection();
            String sql = "update usr SET usr = ?,pwd = ? where mail = ?";
            p = c.prepareStatement(sql);
            p.setObject(1,usr.usrID);
            p.setObject(2,usr.usr);
            p.setObject(3,usr.mail);


            int rows = p.executeUpdate();
            if (rows > 0){
                return true;
            }else {
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            JDBC.close(p,c);
        }
    }


    /**
     * 查下用户已经存在
     * @param mail
     * @return Usr
     */
    public  Usr haveUsr(String mail){
        Connection c  = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;

        Usr usr = new Usr("","","","");

        if (mail.length()< 0) return usr;

        try {
            c = JDBC.GetConnection();
            String sql = "select * from usr where mail = ?";
            p = c.prepareStatement(sql);
            p.setObject(1,usr);

            resultSet = p.executeQuery();
            while (resultSet.next()){
                String pwd = resultSet.getString("pwd");
                String tmpUsr = resultSet.getString("usr");
                String uId = resultSet.getString("usrId");
                String mali = resultSet.getString("mail");
                usr = new Usr(uId,tmpUsr,pwd,mail);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBC.close(p,c);
        }

        return usr;
    }



    /**
     * 此方法用户产生随机数字母和数字
     * @return
     */
    private  char randomChar(){
        //1:定义验证需要的字母和数字
        String string = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789";
        //2：定义随机对象
        Random random = new Random();
        return string.charAt(random.nextInt(string.length()));
    }

}
