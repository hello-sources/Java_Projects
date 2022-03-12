package com.test.JDBC.resultset;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
@SuppressWarnings("all")
public class ResultSet_ {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");

        //1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        //2.得到连接
        Connection connection = DriverManager.getConnection(url, user, password);

        //3.得到Statement
        Statement statement = connection.createStatement();

        //4.组织SQL
        String sql = "select id, name, sex, borndate from actor";
        //执行给定的SQL语句，该语句返回单个ResuleSet对象
        ResultSet resultSet = statement.executeQuery(sql);

        /**
         * ResultSet底层源码，对象结构
         * */

        //5.使用While取出对象
        while (resultSet.next()) {//next方法让光标向后移动，如果没有更多的行，则返回false
            int id = resultSet.getInt(1); //获取该行的第一列数据
            int id1 = resultSet.getInt("id");//通过使用列名来获取列的数据
            String name = resultSet.getString(2);
            String sex = resultSet.getString(3);
            Date date = resultSet.getDate(4);
            System.out.println(id + "\t" + name + "\t" + sex + "\t" + date);

        }
        //6.关闭连接
        resultSet.close();
        statement.close();
        connection.close();
    }
}
