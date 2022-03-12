package com.test.JDBC.statement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

@SuppressWarnings("all")
public class Statement_ {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

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

        //3.得到Statement
        Statement statement = connection.createStatement();

        //4.组织SQL
        String sql = "select name, pwd from admin where name = '"
                + admin_name + "' and pwd = '" + admin_pwd + "' ";
        //执行给定的SQL语句，该语句返回单个ResuleSet对象
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {//如果查询到一条记录，则说明该管理存在
            System.out.println("恭喜，登录成功");
        } else {
            System.out.println("对不起，登录失败");
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
}
