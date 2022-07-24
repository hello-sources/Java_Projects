package com.ict.mybatis.test;

import com.ict.mybatis.mapper.ParameterMapper;
import com.ict.mybatis.pojo.User;
import com.ict.mybatis.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterMapperTest {

    @Test
    public void testCheckLoginByParam() {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        User user = mapper.checkLoginByParam("张三", "123");
        System.out.println(user);
    }

    @Test
    public void testInsertUser() {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        User user = new User();
        user.setUsername("alex");
        user.setPassword("123456");
        user.setAge(12);
        user.setEmail("1234@098.com");
        user.setSex("男");
        int i = mapper.insertUser(user);
        System.out.println(i);
    }

    @Test
    public void testCheckLoginByMap() {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("username", "张三");
        map.put("password", "123");
        User user = mapper.checkLoginByMap(map);
        System.out.println(user);
    }

    @Test
    public void testCheckLogin() {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        User user = mapper.checkLogin("张三", "123");
        System.out.println(user);
    }

    @Test
    public void getUserByUsername() {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        User user = mapper.getUserByUsername("张三");
        System.out.println(user);
    }

    @Test
    public void getAllUser() {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        List<User> list = mapper.getAllUser();
        list.forEach(user-> System.out.println(user));
    }

    @Test
    public void testJDBC() throws Exception{
        String username = "admin";
        Class.forName("");
        Connection connection = DriverManager.getConnection("", "", "");
        PreparedStatement preparedStatement = connection.prepareStatement("select * from t_user where username = ?");
        preparedStatement.setString(1, username);
    }
}
