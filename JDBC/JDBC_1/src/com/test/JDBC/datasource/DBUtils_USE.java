package com.test.JDBC.datasource;

import com.test.JDBC.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBUtils_USE {
    //使用Apache-DBUtils工具类 + Druid连接池完成对表的CRUD操作
    @Test
    public void testQueryMany() throws SQLException { //返回的是多行的结果
        //1.得到连接Druid
        Connection connection = JDBCUtilsByDruid.getConnection();
        //2.使用DBUtils类和接口，先引入DBUtils的相关jar包
        //3.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner();
        //4.就可以执行相关方法，返回ArrayList结果集
        //String sql = "select * from actor where id >= ?";
        //注意该SQL语句也可以查询部分列
        String sql = "select id, name from actor where id >= ?";

        //（1）query方法就是执行SQL语句，得到ResultSet --封装到 -->ArrayList中
        //（2）返回集合
        //（3）connection连接
        //（4）sql：执行的SQL语句
        //（5）new BeanListHandler<>(Actor.class):在将resultSet -> Actor对象 -> 封装到ArrayList
        //     底层使用反射机制，获取Actor属性，然后进行封装
        //（6）1就是给SQL语句中的?赋值，可以有多个值，是个可变参数Object... params
        //（7）底层得到的resultSet会在query中关闭，关闭PreparedStatement

        /**
         * 分析queryRunner.query方法
         * public <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
         *         PreparedStatement stmt = null; //定义preparedStatement
         *         ResultSet rs = null;//接受返回的resultSet
         *         Object result = null;//返回ArrayList
         *
         *         try {
         *             stmt = this.prepareStatement(conn, sql);//创建preparedstatement
         *             this.fillStatement(stmt, params);//对SQL进行?赋值
         *             rs = this.wrap(stmt.executeQuery());//执行SQL，返回resultSet
         *             result = rsh.handle(rs);//返回的resultset -->arrayList[使用到反射，对传入的class对象进行处理]
         *         } catch (SQLException var33) {
         *             this.rethrow(var33, sql, params);
         *         } finally {
         *             try {
         *                 this.close(rs);//关闭resultset
         *             } finally {
         *                 this.close((Statement)stmt);//关闭preparedstatement
         *             }
         *         }
         *         return result;
         *     }
         * */
        List<Actor> list = queryRunner.query(connection, sql, new BeanListHandler<>(Actor.class), 1);
        System.out.println("输出这个集合的信息");
        for (Actor actor : list) {
            System.out.println(actor);
        }

        //释放资源
        JDBCUtilsByDruid.close(null, null, connection);
    }

    //演示apache-dbutils + druid完成返回的结果是单行记录（单个对象）
    @Test
    public void testQuerySingle() throws SQLException {
        //1.得到连接Druid
        Connection connection = JDBCUtilsByDruid.getConnection();
        //2.使用DBUtils类和接口，先引入DBUtils的相关jar包
        //3.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner();
        //4.就可以执行相关方法，返回单个对象
        String sql = "select * from actor where id = ?";
        //因为我们返回的是单行记录，对应是单个对象，使用Handler是BeanHandler
        Actor actor = queryRunner.query(connection, sql, new BeanHandler<>(Actor.class), 2);
        System.out.println(actor);

        //释放资源
        JDBCUtils.close(null, null, connection);
    }

    //演示apache-dbutils + druid完成查询结果是单行单列，返回的是object
    @Test
    public void testScalar() throws SQLException {
        Connection connection = JDBCUtilsByDruid.getConnection();

        QueryRunner queryRunner = new QueryRunner();
        //执行相关方法，返回单列单行，返回的就是object
        String sql = "select name from actor where id = ?";
        //因为返回的是对象，使用handle就是ScalarHandler
        Object obj = queryRunner.query(connection, sql, new ScalarHandler(), 5);
        System.out.println(obj);
        JDBCUtilsByDruid.close(null, null, connection);
    }


    //演示apache-dbutils + druid 完成DML语句
    @Test
    public void testDML() throws SQLException {
        Connection connection = JDBCUtilsByDruid.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        //组织SQL语句，完成Updat， insert，delete
        //String sql = "update actor set name = ? where id = ?";
        //String sql = "insert into actor values(null, ?, ?, ?, ?)";
        String sql = "delete from actor where id = ?";
        //(1)执行dml的是queryRunner.update()
        //(2)返回的是受影响的行数
        int affectedRow = queryRunner.update(connection, sql, 70);
        System.out.println(affectedRow > 0 ? "执行成功" : "执行没有影响到表");
        JDBCUtilsByDruid.close(null, null, connection);
    }
}
