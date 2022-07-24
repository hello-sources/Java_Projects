package com.ict.mybatis.mapper;

import com.ict.mybatis.pojo.User;

import java.util.List;

public interface UserMapper {
    /**
     * MyBatis面向接口编程的两个一致
     * 1、mapper映射文件的namespace要和mapper接口的全类名保持一致
     * 2、映射文件中，SQL语句的ID要和mapper接口中的方法一致
     *
     * 表--实体类--mapper接口--映射文件
     */

    /**
     * 添加用户信息
     */
    int insertUser();

    /**
     * 修改用户信息
     */
    void updateUser();

    /**
     * 删除用户信息
     */
    void deleteUser();

    /**
     * 根据Id查询用户信息
     */
    User getUserById();

    /**
     * 查询所有用户信息
     */
    List<User> getAllUser();
}
