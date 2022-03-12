package com.test.JDBC;

import com.mysql.jdbc.Driver;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 分析Java连接MySQL的五种方式
 * */
public class JdbcConn {
    @Test
    public void connect01() throws SQLException {
        Driver driver = new Driver();
        String url = "jdbc:mysql://localhost:3306/testdb?characterEncoding=utf-8";
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "123456");
        Connection connection = driver.connect(url, properties);
        System.out.println("第1种方式" + connection);
    }

    @Test
    public void connect02() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        //使用反射加载Driver类, 动态加载，更加灵活，减少依赖性
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver)aClass.newInstance();
        String url = "jdbc:mysql://localhost:3306/testdb?characterEncoding=utf-8";
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "123456");
        Connection connect = driver.connect(url, properties);
        System.out.println("第2种方式" + connect);
    }

    //方式3使用DriverManager替代Driver进行统一管理
    @Test
    public void connect03() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        //使用反射加载Driver
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver)aClass.newInstance();

        //创建url和user以及password
        String url = "jdbc:mysql://localhost:3306/testdb?characterEncoding=utf-8";
        String user = "root";
        String password = "123456";

        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("第三种方式 ： " + connection);
    }

    //方式4：使用Class.forName自动完成注册驱动，简化代码
    //这种方式获取连接是使用最多的
    @Test
    public void connect04() throws ClassNotFoundException, SQLException {
        //使用反射加载Driver类
        //在加载Driver类时，完成注册
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        /**
         * 源码：1.静态代码块，在类加载时，会执行一次
         *      2.DirverManeger.registerDriver(new Driver());
         *      3.因此注册Driver的工作已经完成
         *      static {
         *          try {
         *              DriverManager.registerDriver(new Driver());
         *          } catch (SQLException var1) {
         *              throw new RuntimeException("can't register driver!");
         *          }
         *      }
         * */

        //创建url和user以及password
        String url = "jdbc:mysql://localhost:3306/testdb?characterEncoding=utf-8";
        String user = "root";
        String password = "123456";

        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("方式4 ： " + connection);
    }

    //方式5，在方式4的基础上改进，增加配置文件，让连接MySQL更加灵活
    @Test
    public void connect05() throws IOException, ClassNotFoundException, SQLException {

        //通过properties对象获取配置文件信息
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("方式5 ：" + connection);
    }
}
