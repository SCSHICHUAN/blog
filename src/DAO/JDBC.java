package DAO;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.*;
public class JDBC {



    private static final ComboPooledDataSource dataSource = new ComboPooledDataSource();
    /**
     * 重链接池中获取链接，参数已经配置到c3p0-config.xml中
     */
    public static Connection GetConnection()throws Exception{
        Connection connection = dataSource.getConnection();
        return  connection;
    }

    /**
     * 释放资源
     * @author SHICHUAN
     */
    public static void close(Statement statement, Connection connection){
        if(statement != null){
            try {
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            statement = null;
        }
        if(connection != null){
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            connection = null;
        }

    }
    public static void close(Statement statement, Connection connection, ResultSet resultSet){
        if(statement != null){
            try {
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            statement = null;
        }
        if(connection != null){
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            connection = null;
        }
        if(resultSet != null){
            try {
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            resultSet = null;
        }

    }


}
