package com.test.JDBC.preparedstatment;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

@SuppressWarnings("all")
public class PreparedStatement_ {
    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);

        //让用户输入管理员名和密码
        System.out.println("请输入管理员名字："); // next() or 1空格或者单引号表示结束
        String admin_name = scanner.nextLine(); //nextline() 用回车表示结束
        System.out.println("请输入管理员密码");
        String admin_pwd = scanner.nextLine();

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

        //3.得到PreparedStatement
        //3.1组织SQL,SQL语句的?相当于占位符
        String sql = "select name, pwd from admin where name = ? and pwd = ?";

        //3.2 preparedStatement对象实现了PreparedStatement接口的实现类 对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //3.3 给？赋值
        preparedStatement.setString(1, admin_name);
        preparedStatement.setString(2, admin_pwd);

        //4.执行select语句使用executeQuery
        //如果执行的是dml(update, insert, delete) executeUpdate()方法
        //这里执行executeQuery，不要写在SQL
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {//如果查询到一条记录，则说明该管理存在
            System.out.println("恭喜，登录成功");
        } else {
            System.out.println("对不起，登录失败");
        }

        //关闭连接
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
