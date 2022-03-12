package com.test.JDBC.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.test.JDBC.utils.JDBCUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class C3P0_ {
    //方式一：相关参数，在程序中指定User,url,password等
    @Test
    public void testC3P0_01() throws Exception {
        //1.创建一个数据源对象
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        //2.通过配置文件mysql.properties获取相关信息
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");

        //给数据源comboPooledDataSource设置相关参数
        //注意：连接管理是由comboPooledDataSource
        comboPooledDataSource.setDriverClass(driver);
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);

        //设置初始化连接数
        comboPooledDataSource.setInitialPoolSize(10);
        //最大连接数
        comboPooledDataSource.setMaxPoolSize(50);
        //测试连接池的效率，测试连接MySQL5000次操作
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            Connection connection =  comboPooledDataSource.getConnection();//这个方法就是从DataSource接口实现的
            //System.out.println("连接成功");
            connection.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("C3P0 5000连接MySQL 耗时 = " + (end - start));
    }

    //方式2，使用配置文件模板来完成连接
    //1.将C3P0提供的c3p0.config.xml拷贝到src目录下
    //2.该文件制定了连接数据库和连接池的相关参数
    @Test
    public void testC3P0_02()  throws SQLException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("hello_mysql");
        //测试5000次连接MySQL
        long start = System.currentTimeMillis();
        System.out.println("开始执行-----");
        for (int i = 0; i < 500000; i++) {
            Connection connection = comboPooledDataSource.getConnection();
            //System.out.println("连接OK");
            connection.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("C3P0的第二种方式，耗时 = " + (end - start));
    }
}
