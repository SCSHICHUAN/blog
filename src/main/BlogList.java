package main;

import DAO.JDBC;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BlogList {



    public static void list(HttpServletRequest request,HttpServletResponse response){
        try {
            request.getRequestDispatcher("/views/blogList.jsp").forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void blogList(HttpServletRequest request, HttpServletResponse response){


        JSONObject json = new JSONObject();
        ArrayList names = getBlogs();
        json.put("listName",names);

        try {
            response.getOutputStream().write(json.toString().getBytes("utf8"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     *
     * @param blogName
     * @return all blog list
     */
    private static ArrayList<String> getBlogs(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<String> names = new ArrayList<>();

        try {
            connection = JDBC.GetConnection();
            String sql = "select * from blogName";
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String tmpBlogName = resultSet.getString("blogName");
                names.add(tmpBlogName);
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
