package com.ict.mybatis.mapper;

import com.ict.mybatis.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ParameterMapper {

    User checkLoginByParam(@Param("username") String username, @Param("password") String password);

    int insertUser(User user);

    User checkLoginByMap(Map<String, Object> map);

    List<User> getAllUser();

    User getUserByUsername(String username);

    //验证登录
    User checkLogin(String username, String password);
}

