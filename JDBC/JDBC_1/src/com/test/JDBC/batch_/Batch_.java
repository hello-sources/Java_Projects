package com.test.JDBC.batch_;

import com.test.JDBC.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Batch_ {
    //传统方法，添加5000条数据到admin2
    @Test
    public void noBatch() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into admin2 values(null, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("开始执行");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            preparedStatement.setString(1, "jack" + i);
            preparedStatement.setString(2, "666");
            preparedStatement.executeUpdate();
        }
        long end = System.currentTimeMillis();
        System.out.println("传统的方式耗时：" + (end - start));
        //关闭连接
        JDBCUtils.close(null, preparedStatement, connection);
    }

    //使用批量处理数据
    @Test
    public void Batch() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into admin2 values(null, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("开始执行");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            preparedStatement.setString(1, "jacky" + i);
            preparedStatement.setString(2, "666");
            //将sql语句加入批处理包中
            /**
             *  public void addBatch() throws SQLException {
             *         synchronized(this.checkClosed().getConnectionMutex()) {
             *             if (this.batchedArgs == null) {
             *             //1.创建一个ArrayList -> elementData => Object[]
             *             //2.elementData => Object[] 就会存放我们预处理的Sql语句
             *             //3.当elementData满了之后，就进行扩容
             *             //4.当添加到指定的值后，就executeBatch
             *             //5.批处理会减少我们发送SQL语句的网络开销，而且减少编译次数，因此提高效率
             *                 this.batchedArgs = new ArrayList();
             *             }
             *
             *             for(int i = 0; i < this.parameterValues.length; ++i) {
             *                 this.checkAllParametersSet(this.parameterValues[i], this.parameterStreams[i], i);
             *             }
             *
             *             this.batchedArgs.add(new PreparedStatement.BatchParams(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths, this.isNull));
             *         }
             *     }
             * */
            preparedStatement.addBatch();
            //当有1000条记录时，再批量执行
            if ((i + 1) % 1000 == 0) { //满1000条语句
                preparedStatement.executeBatch();
                //清空一把
                preparedStatement.clearBatch();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("批处理方式耗时：" + (end - start));
        //关闭连接
        JDBCUtils.close(null, preparedStatement, connection);
    }
}
