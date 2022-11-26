package main;

import DAO.JDBC;
import Utils.SaveUserLogin;
import model.BlogName;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;

public class BlogList {



    public static void list(HttpServletRequest request,HttpServletResponse response,String url){

        try {
            request.getRequestDispatcher(url).forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void blogList(HttpServletRequest request, HttpServletResponse response,String type){


        SaveUserLogin saveUserLogin = new SaveUserLogin();
        String usrID = saveUserLogin.loginCookis(request,"blogCookNameID");



        JSONArray jsonArray = new JSONArray();
        if(Objects.equals(type,"world")){
              jsonArray =  ArrayListTOJSONarray(getBlogs());
        }else if (Objects.equals(type,"my")){
            jsonArray =  ArrayListTOJSONarray(getBlogs(usrID));
        }


        try {
            response.getOutputStream().write(jsonArray.toString().getBytes("utf8"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static JSONArray ArrayListTOJSONarray(ArrayList<BlogName> list){
        JSONArray jsonArray = new JSONArray();

        for (BlogName testIteam : list) {

            /**
             * 把 java-Object 转换为 json-Object
             */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", testIteam.id);
            jsonObject.put("usrID", testIteam.usrID);
            jsonObject.put("usrName", testIteam.usrName);
            jsonObject.put("blogName", testIteam.blogName);
            jsonObject.put("createDate", testIteam.createDate);
            jsonObject.put("updateDate", testIteam.updateDate);

            jsonArray.put(jsonObject);
        }


        return jsonArray;
    }


    /**
     * @return all blog list
     */
    private static ArrayList<BlogName> getBlogs(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<BlogName> names = new ArrayList<>();

        try {
            connection = JDBC.GetConnection();
            String sql = "select * from blogName order by createDate desc";
            preparedStatement = connection.prepareStatement(sql);

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
                    blogName.updateDate = "无";
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



    /**
     * @return all blog list
     */
    private static ArrayList<BlogName> getBlogs(String usrID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<BlogName> names = new ArrayList<>();

        try {
            connection = JDBC.GetConnection();
            String sql = "select * from blogName where usrID = ? order by createDate desc";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,usrID);

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
                    blogName.updateDate = "无";
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
