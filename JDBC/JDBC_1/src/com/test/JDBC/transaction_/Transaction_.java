package com.test.JDBC.transaction_;

import com.test.JDBC.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transaction_ {

    //没有使用事务
    @Test
    public void noTransaction() {
        //操作转账的业务
        //1.得到连接
        Connection connection = null;

        //2.组织一个sql
        String sql1 = "update account set balance = balance - 100 where id = 1";
        String sql2 = "update account set balance = balance + 100 where id = 2";
        PreparedStatement preparedStatement = null;
        //3.创建PreparedStatement对象
        try {
            connection = JDBCUtils.getConnection(); //在默认情况下，connection对象是默认自动提交
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();

            int i = 1 / 0;
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.close(null, preparedStatement, connection);
        }
    }

    //用事务来解决
    @Test
    public void Transaction() {
        //操作转账的业务
        //1.得到连接
        Connection connection = null;

        //2.组织一个sql
        String sql1 = "update account set balance = balance - 100 where id = 1";
        String sql2 = "update account set balance = balance + 100 where id = 2";
        PreparedStatement preparedStatement = null;
        //3.创建PreparedStatement对象
        try {
            connection = JDBCUtils.getConnection(); //在默认情况下，connection对象是默认自动提交
            //将connection设置为不自动提交
            connection.setAutoCommit(false);//相当于开启了事务
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();

            //int i = 1 / 0;
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();

            //在这里提交事务
            connection.commit();
        } catch (SQLException e) {
            //这里我们可以进行回滚，即撤销执行的SQL
            //默认回滚到事务开始的状态
            System.out.println("执行发生了异常，撤销执行的SQL");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.close(null, preparedStatement, connection);
        }
    }
}
