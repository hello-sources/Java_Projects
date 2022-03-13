package com.edu.mhl.service;

import com.edu.mhl.dao.DiningTableDAO;
import com.edu.mhl.domain.DiningTable;

import java.util.List;

public class DiningTableService {
    //定义一个DiningTableDAO对象
    private DiningTableDAO diningTableDAO = new DiningTableDAO();

    //返回所有餐桌的信息
    public List<DiningTable> list() {
        List<DiningTable> diningTables = diningTableDAO.queryMulti("select id, state from diningTable", DiningTable.class);
        return diningTables;
    }

    //根据Id，查询对应的餐桌DiningTable对象
    //如果null，表示id编号对应的惭怍不存在
    public DiningTable getDiningTableById(int id) {
        //为了避免SQL语句错误，可以先到数据库执行试试
        return diningTableDAO.querySingle("select * from diningTable where id = ?", DiningTable.class, id);
    }

    //如果餐桌可以预定，调用方法对其状态进行更新（包括预订人的电话和名字）
    public boolean orderDiningTable(int id, String orderName, String orderTel) {
        int update =
                diningTableDAO.update("update diningTable set state = '已经预定', orderName = ?, orderTel = ? where id = ?", orderName, orderTel, id);
        return update > 0;
    }

    //需要提供一个更新餐桌状态的方法
    public boolean updateDiningTableState(int id, String state) {
        int update = diningTableDAO.update("update diningTable set state = ? where id = ?", state, id);
        return update > 0;
    }

    //提供方法，将指定餐桌设置为空闲状态
    public boolean updateDiningTableToFree(int id, String state) {
        int update = diningTableDAO.update("update diningTable set state = ?, orderName = '', orderTel = '' where id = ?", state, id);
        return update > 0;
    }
}
