package com.ssm.service.impl;

import com.ssm.mapper.AdminMapper;
import com.ssm.pojo.Admin;
import com.ssm.pojo.AdminExample;
import com.ssm.service.AdminService;
import com.ssm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    //业务逻辑层，一定有数据访问层对象
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andANameEqualTo(name);
        List<Admin> list = adminMapper.selectByExample(adminExample);
        if (list.size() > 0) {
            Admin admin = list.get(0);
            String mipwd = MD5Util.getMD5(pwd);
            if (mipwd.equals(admin.getaPass())) {
                return admin;
            }
        }
        return null;
    }
}