package main;

import DAO.JDBC;
import model.BlogName;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CreateBlog {

    private static HttpServletResponse response;


    public static void addBlog(HttpServletRequest req,HttpServletResponse res){
        response = res;

        String html = (String) req.getParameter("html");
        String blogName = (String) req.getParameter("blogName");
        String canEdit = (String) req.getParameter("canEdit");



        if (getBlogName(blogName)){
            if (Objects.equals(canEdit,"yes")){
                saveBlogHtml(blogName,html,false);
            }else {
                creatBlogFailed(req,res);
            }
        }else {
            saveBlogHtml(blogName,html,true);
        }


    }


   //创建失败返回
    private static void creatBlogFailed(HttpServletRequest request,HttpServletResponse response){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","creatBlogSameName");

        try {
            response.getOutputStream().write(jsonObject.toString().getBytes("utf8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //保存创建blog
    private static void saveBlogHtml(String blogName,String html,boolean b){

        String sql = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<link href=\"https://cdn.jsdelivr.net/npm/prismjs@v1.x/themes/prism.css\" rel=\"stylesheet\" />\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/prismjs@v1.x/components/prism-core.min.js\"></script>\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/prismjs@v1.x/plugins/autoloader/prism-autoloader.min.js\"></script>" +
                "\t<title>石川博客</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t"+html+"\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        //输出文件1029
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        Date now = new Date();
//        String pathFile= "/Users/stan/Desktop/"+sdf.format(now)+".html";
//       String pathFile= "/Users/stan/Desktop/"+blogName+".html";
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

            if (b){
                //mysql save name
                BlogName blogName1 = new BlogName(null,"1","test",blogName,null,null);
                AddBlogName(blogName1);
            }else {
                updateTime(blogName);
            }

            responesToCline(response,blogName);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //保存成功后返回
    private static void responesToCline(HttpServletResponse response, String url) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","creatBlogSuccess");
        jsonObject.put("url","https://stanserver.cn:444/blog/"+url+".html");
        try {
            response.getOutputStream().write(jsonObject.toString().getBytes("utf8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param blogName
     * @return 数据库操作成功
     */
    public  static  boolean AddBlogName(BlogName blogName){
        Connection c  = null;
        PreparedStatement p = null;
        Date date = new Date();

        try {
            c = JDBC.GetConnection();
            String sql = "insert blogName(usrID,usrName,blogName) values(?,?,?)";
            p = c.prepareStatement(sql);
            p.setObject(1,blogName.usrID);
            p.setObject(2,blogName.usrName);
            p.setObject(3,blogName.blogName);

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
     *
     * @param blogName
     * @return 检查是已经存在同名blog
     */
    public static boolean getBlogName(String blogName){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBC.GetConnection();
            String sql = "select * from blogName where blogName = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,blogName);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String tmpBlogName = resultSet.getString("blogName");
                System.out.print(tmpBlogName);
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBC.close(preparedStatement,connection);
        }

        return false;
    }

    /**
     *
     * @param blogName
     * @return 数据库操作成功
     */
    public  static  boolean updateTime(String blogName){
        Connection c  = null;
        PreparedStatement p = null;
        Date date = new Date();

        try {
            c = JDBC.GetConnection();
            String sql = "UPDATE blogName set updateDate = ?  WHERE blogName = ?";
            p = c.prepareStatement(sql);
            p.setObject(1,date);
            p.setObject(2,blogName);


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


}
