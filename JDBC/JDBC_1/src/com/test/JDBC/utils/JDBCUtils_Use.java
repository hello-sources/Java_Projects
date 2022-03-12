package com.test.JDBC.utils;

import org.junit.Test;

import java.sql.*;

public class JDBCUtils_Use {
    public static void main(String[] args) {

    }

    @Test
    public void testSelect() {
        //1.得到连接
        Connection connection = null;

        //2.组织一个sql
        String sql = "select * from actor";
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        //3.创建PreparedStatement对象
        try {
            connection = JDBCUtils.getConnection();
            System.out.println(connection.getClass());
            //使用的是com.mysql.jdbc.JDBC4Connection
            preparedStatement = connection.prepareStatement(sql);

            //执行
            set = preparedStatement.executeQuery();
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                String sex = set.getString("sex");
                Date borndate = set.getDate("borndate");
                String phone = set.getString("phone");
                System.out.println(id + "\t" + name + "\t" + sex + "\t" + borndate + "\t" + phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.close(set, preparedStatement, connection);
        }
    }

    @Test
    public void testDML() { //insert, update, delete
        //1.得到连接
        Connection connection = null;

        //2.组织一个sql
        String sql = "update actor set name = ? where id = ?";
        //测试delete语句，insert语句
        PreparedStatement preparedStatement = null;
        //3.创建PreparedStatement对象
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            //给占位符赋值
            preparedStatement.setString(1, "周星驰");
            preparedStatement.setInt(2, 7);
            //执行
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.close(null, preparedStatement, connection);
        }
    }
}
