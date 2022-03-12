package com.test.JDBC.datasource;

import com.test.JDBC.utils.JDBCUtils;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings("all")
public class JDBCUtilsByDruid_USE {
    @Test
    public void testSelect() {
        System.out.println("使用Druid方式完成");
        //1.得到连接
        Connection connection = null;

        //2.组织一个sql
        String sql = "select * from actor where id >= ?";
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        //3.创建PreparedStatement对象
        try {
            connection = JDBCUtilsByDruid.getConnection();
            System.out.println(connection.getClass());
            //运行的是com.alibaba.druid.pool.DruidPooledConnection
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);

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
            JDBCUtilsByDruid.close(set, preparedStatement, connection);
        }
    }


    //使用土方法解决ResultSet =封装=> ArrayList
    @Test
    public void testSelectToArrayList() {
        System.out.println("使用Druid方式完成");
        //1.得到连接
        Connection connection = null;

        //2.组织一个sql
        String sql = "select * from actor where id >= ?";
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        ArrayList<Actor> list = new ArrayList<>();//创建ArrayList对象，存放actor对象
        //3.创建PreparedStatement对象
        try {
            connection = JDBCUtilsByDruid.getConnection();
            System.out.println(connection.getClass());
            //运行的是com.alibaba.druid.pool.DruidPooledConnection
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);

            //执行
            set = preparedStatement.executeQuery();
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                String sex = set.getString("sex");
                Date borndate = set.getDate("borndate");
                String phone = set.getString("phone");
                //System.out.println(id + "\t" + name + "\t" + sex + "\t" + borndate + "\t" + phone);
                //把得到的resultSet记录，封装到Actor对象，放入到list集合
                list.add(new Actor(id, name, sex, borndate, phone));
            }
            System.out.println("List集合数据 = " + list);
            for (Actor actor : list) {
                System.out.println("id=" + actor.getId() + ",name" + actor.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtilsByDruid.close(set, preparedStatement, connection);
        }
        //因为ArrayList和Connection没有任何关联，所以该集合可以复用
        //return list;
    }
}
