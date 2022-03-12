package com.test.JDBC.datasource;

import com.test.JDBC.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

public class ConQuestion {
    //代码连接MySQL5000次
    @Test
    public void testCon() {
        //看看连接Connection会耗用多久
        long start = System.currentTimeMillis();
        System.out.println("开始连接......");
        for (int i = 0; i < 5000; i++) {
            //使用传统的JDBC，得到连接
            Connection connection = JDBCUtils.getConnection();
            //做一些工作，比如得到PreparedStatem，发送SQL
            //。。。。。
            //关闭
            JDBCUtils.close(null, null, connection);
        }
        long end = System.currentTimeMillis();
        System.out.println("传统方式连接5000次，耗时 = " + (end - start));
    }
}
