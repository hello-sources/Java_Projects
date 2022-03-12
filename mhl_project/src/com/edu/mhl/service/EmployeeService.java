package com.edu.mhl.service;

import com.edu.mhl.dao.EmployeeDAO;
import com.edu.mhl.domain.Employee;

/**
 * 该类完成对employee表的操作（通过调用EmployeeDAO操作）
 *  */
public class EmployeeService {
    //定义一个EmployeeDAO属性
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    //方法，根据empId和pwd返回一个Employee对象
    //如果查询不到，就返回null
    public Employee  getEmployeeByIdAndPwd(String empId, String pwd) {
        Employee employee =
                employeeDAO.querySingle("select * from employee where empId = ? and pwd = md5(?)", Employee.class, empId, pwd);
        return employee;
    }
}
