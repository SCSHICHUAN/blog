package server;

import DAO.JDBC;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String html = (String) request.getParameter("html");
        String blogName = (String) request.getParameter("blogName");
        System.out.print(html);
        saveBlog(blogName,html);
        responesToCline(response,blogName);



         mainAdd("石川");


        RequestDispatcher rd = request.getRequestDispatcher("https://stanserver.cn:444/blog/"+blogName+".html");
        rd.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    private void saveBlog(String blogName,String str){

        String sql = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<link href=\"https://cdn.jsdelivr.net/npm/prismjs@v1.x/themes/prism.css\" rel=\"stylesheet\" />\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/prismjs@v1.x/components/prism-core.min.js\"></script>\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/prismjs@v1.x/plugins/autoloader/prism-autoloader.min.js\"></script>" +
                "\t<title>石川博客</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t"+str+"\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        //输出文件1029
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        Date now = new Date();
//        String pathFile= "/Users/stan/Desktop/"+sdf.format(now)+".html";
//        String pathFile= "/Users/stan/Desktop/"+blogName+".html";
        String pathFile= "/root/webRTC/public/blog/"+blogName+".html";
        File file = new File(pathFile);
        try {
            FileOutputStream fop = new FileOutputStream(file);
            if(!file.exists()){
                file.createNewFile();
            }
            byte[] contentInBytes = sql.getBytes();
            System.out.println("contentInBytes===="+contentInBytes);
            fop.write(contentInBytes);
            //先清空缓冲区数据,保证缓存清空输出
            fop.flush();
            //关闭此文件输出流并释放与此流有关的所有系统资源
            fop.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private  void responesToCline(HttpServletResponse response, String url) {
        String tmpStr =  "https://stanserver.cn:444/blog/"+url+".html";
        try {
            response.getOutputStream().write(tmpStr.getBytes("utf8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     *数据库插入数据
     */
    public String mainAdd(String username) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String resultSet = null;



        try {
            connection = JDBC.GetConnection();
            /**
             *用占位符号来防止sql注入
             */
            String sql = "insert into t1 values(null , ?)";
            /**
             * 预编译SQL
             */
            preparedStatement = connection.prepareStatement(sql);
            /**
             * 设置参数
             */
            preparedStatement.setObject(1, username);
            int row = preparedStatement.executeUpdate();
            if (row>0){
                resultSet = "插入数据成功";
            }else {
                resultSet = "插入数据失败";
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC.close(preparedStatement, connection);
        }

        return resultSet;
    }

}
