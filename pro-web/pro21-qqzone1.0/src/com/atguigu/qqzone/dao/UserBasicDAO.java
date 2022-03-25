package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

public interface UserBasicDAO {
    //根据账号获取用户信息
    public UserBasic getUserBasic(String loginId, String pwd);

    //获取指定用户好友列表
    public List<UserBasic> getUserBasicList(UserBasic userBasic);

    //根据Id查询
    UserBasic getUserBasicById(Integer id);
}
