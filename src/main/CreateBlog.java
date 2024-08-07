package main;


import DAO.JDBC;
import Utils.SaveUserLogin;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import Share.Share;

public class CreateBlog {

    private static HttpServletRequest  request;
    private static HttpServletResponse response;



    public static void addBlog(HttpServletRequest req,HttpServletResponse res){
        request = req;
        response = res;

        String html = (String) req.getParameter("html");
        String blogName = (String) req.getParameter("blogName");
        String categoryName = (String) req.getParameter("categoryName");
        String canEdit = (String) req.getParameter("canEdit");
        String totalText = (String) req.getParameter("totalText");



        if (getBlogName(blogName)){
            if (Objects.equals(canEdit,"yes")){
                saveBlogHtml(blogName,totalText,html,false,categoryName);
            }else {
                creatBlogFailed(req,res);
            }
        }else {
            saveBlogHtml(blogName,totalText,html,true,categoryName);
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


    //保存创建blog文件 和保存数据库记录
    private static void saveBlogHtml(String blogName,String totalText,String html,boolean b,String categoryName){


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();

        String createTime = "";
        String editTime = "";


         if (b){
             createTime = sdf.format(now);
             editTime = "no";
         }else {
             ArrayList<BlogName> blogs = getBlogs(blogName);
             BlogName blogName1= blogs.get(0);
             createTime = blogName1.createDate;
             editTime = sdf.format(now);
         }

        String blogHeaderTitle = "<div class=\"bolgHeader\" style=\"\n" +
                "\tbackground: rgb(71,148,255);\n" +
                "\twidth: 100%; \n" +
                "\tpadding-top: 10px;\n" +
                "\tpadding-bottom:10px;\n" +
                "\tdisplay: flex;\n" +
                "\tjustify-content: flex-start;\">\n" +
                "\t<div style=\"\n" +
                "\tcolor: white;\n" +
                "\ttext-align: left;\n" +
                "\tmax-width: 80%;\n" +
                "\tmargin-left:2.5%; \n" +
                "\tword-wrap:break-word\">" +
                "<code><h2 style=\"margin: 0px \"> "+blogName+ "</h2>\n" +
                "<h3 style=\"margin: 10px 0px 0px  0px\">"+categoryName+"</h3>\n" +
                "\tText:"+totalText+" Create:"+createTime+" Edit:"+editTime+"</code>\n" +
                "</div>\n" +
                "</div>";



        String htmlTmp = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<link rel=\"icon\" href=\"https://stanserver.cn:444/blog/108.png\">\n"+
                "<link href=\"https://cdn.jsdelivr.net/npm/prismjs@v1.x/themes/prism.css\" rel=\"stylesheet\" />\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/prismjs@v1.x/components/prism-core.min.js\"></script>\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/prismjs@v1.x/plugins/autoloader/prism-autoloader.min.js\"></script>" +
                "<style type=\"text/css\">\n" +
                "blockquote {\n" +
                "    background-color: #f5f2f0;\n" +
                "    border-left: 8px solid #B4D5FF;\n" +
                "    display: block;\n" +
                "    font-size: 100%;\n" +
                "    line-height: 1.5;\n" +
                "    margin: 10px 0;\n" +
                "    padding: 10px;\n" +
                "}\n" +
                "table, tr, th,td {\n" +
                " \tpadding: 5px;\n" +
                "    border-collapse: collapse;\n" +
                "    border: #ccc 1px solid;\n" +
                "}\n" +
                "th{\n" +
                "\tbackground-color: #f5f2f0;\n" +
                "}\n" +
                "\n" +
                "</style>"+
                "\t<title>"+blogName+"</title>\n" +
                "</head>\n" +
                "<body style=\"margin: 0px;background-color: rgb(240,240,240);\">\n" +
                blogHeaderTitle+
                "<div class=\"edit\" style=\"\n" +
                "margin-top:6px;\n" +
                "margin-right:5%;\n" +
                "margin-left: 5%;\n" +
                "margin-bottom:10px;\n" +
                "background: rgb(255,255,255);\n" +
                "padding: 30px;\n" +
                "border-radius: 3px;min-height: 500px\">"+
                "\t"+html+"\n"+" </div>"+
                "\n" +
                "<div style=\"text-align:center;\"><code>stanserver.cn stanserver@163.com</code></div>"+
                "</body>\n" +
                "</html>";

        //输出文件1029
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
//        Date now = new Date();
        String pathFile= "/Users/stan/Desktop/"+sdf.format(now)+".html";
//        String pathFile= Share.fileHome(request)+blogName+".html";

        File file = new File(pathFile);
        try {
            FileOutputStream fop = new FileOutputStream(file);
            if(!file.exists()){
                file.createNewFile();
            }
            byte[] contentInBytes = htmlTmp.getBytes();
//            System.out.println("contentInBytes===="+contentInBytes);
            fop.write(contentInBytes);
            //先清空缓冲区数据,保证缓存清空输出
            fop.flush();
            //关闭此文件输出流并释放与此流有关的所有系统资源
            fop.close();


            saveBlogToSql(blogName,b);
            responesToCline(response,blogName);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存记录到数据库
     * @param name
     * @param update
     */
    private static void saveBlogToSql(String name,boolean update){


        SaveUserLogin saveUserLogin = new SaveUserLogin();
        String cookieUsr = saveUserLogin.loginCookis(request,"blogCookNameUsr");
        String cookieID = saveUserLogin.loginCookis(request,"blogCookNameID");



        if (update){
            //mysql save name
            BlogName blogName1 = new BlogName(null,cookieID,cookieUsr,name,null,null);
            AddBlogName(blogName1);
        }else {
            updateTime(name);
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
//                System.out.print(tmpBlogName);
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



    /**
     *
     * @param name
     * @return all blog list
     */
    private static ArrayList<BlogName> getBlogs(String name){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<BlogName> names = new ArrayList<>();

        try {
            connection = JDBC.GetConnection();
            String sql = "select * from blogName where blogName = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,name);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BlogName blogName = new BlogName(
                        resultSet.getString("id"),
                        resultSet.getString("usrID"),
                        resultSet.getString("usrName"),
                        resultSet.getString("blogName"),
                        resultSet.getString("createDate"),
                        resultSet.getString("updateDate"));
                if (Objects.equals(resultSet.getString("updateDate"),null)){
                    blogName.updateDate = "no";
                }
                names.add(blogName);
            }
            return names;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBC.close(preparedStatement,connection);
        }

        return null;
    }



}
