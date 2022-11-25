package main;

import DAO.JDBC;
import Utils.MailServer;

import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Random;

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
            Usr usr = new Usr(usrID,usrName,pwd,mail);
            addUsr(usr);
            relust = "success";
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
        Date date = new Date();

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
