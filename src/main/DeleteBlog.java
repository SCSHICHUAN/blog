package main;

import DAO.JDBC;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeleteBlog {


    public static void deleBlog(HttpServletRequest request, HttpServletResponse response){

        String blogName = (String) request.getParameter("blogName");
        String patn = "/root/webRTC/public/blog/"+blogName+".html";
        File file = new File(patn);

        try{
            file.delete();
            deleBlogName(blogName);
            resToClient(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private static void resToClient(HttpServletRequest request, HttpServletResponse response){
        JSONObject obj = new JSONObject();
        obj.put("result","success");
        try {
            response.getOutputStream().write(obj.toString().getBytes("utf8"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     *
     * @param blogName
     * @return dele success
     */
    public static boolean deleBlogName(String blogName){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JDBC.GetConnection();
            String sql = "delete from blogName where blogName = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,blogName);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBC.close(preparedStatement,connection);
        }

        return false;
    }



}
