package com.edu.mhl.service;

import com.edu.mhl.dao.BillDAO;
import com.edu.mhl.dao.MultiTableDAO;
import com.edu.mhl.domain.Bill;
import com.edu.mhl.domain.MultiTableBean;

import java.util.List;
import java.util.UUID;

public class BillService {
    //定义BillDAO属性
    private BillDAO billDAO = new BillDAO();
    //定义MenuService属性
    private MenuService menuService = new MenuService();
    //定义DiningTableService属性
    private DiningTableService diningTableService = new DiningTableService();

    private MultiTableDAO multiTableDAO = new MultiTableDAO();
    //编写点餐方法
    //1.生成账单
    //2.需要更新对应餐桌的状态
    //3.如果成功返回true否则返回false
    public boolean orderMenu(int menuId, int nums, int diningTableId) {
        //生成一个账单号，UUID
        String billID = UUID.randomUUID().toString();

        //将账单生成到Bill表,要求直接计算账单金额
        int update = billDAO.update("insert into bill values(null, ?, ?, ?, ?, ?, now(), '未结账')",
                billID, menuId, nums, menuService.getMenuById(menuId).getPrice() * nums, diningTableId);
        if (update <= 0) return false;
        //需要更新对应餐桌的状态
        return diningTableService.updateDiningTableState(diningTableId, "就餐中");
    }

    //返回所有的账单，提供给View使用
    public List<Bill> list() {
        return  billDAO.queryMulti("select * from bill", Bill.class);
    }

    //返回所有的账单并带有菜品名，提供给View使用
    public List<MultiTableBean> list2() {
        return  multiTableDAO.queryMulti("SELECT bill.*, name , price " +
                "from bill, menu " +
                "WHERE bill.menuId = menu.id;", MultiTableBean.class);
    }

    //查看某个餐桌是否有未结账的账单
    public boolean hasPayBillByDiningTableId(int diningTableId) {
        Bill bill = billDAO.querySingle("SELECT * from bill WHERE diningTableId = ? AND state = '未结账' LIMIT 0,1", Bill.class, diningTableId);
        return bill != null;
    }

    //完成结账[如果餐桌存在，并且该餐桌有未结账的账单]
    //如果成功返回true，失败返回false
    public boolean payBill(int diningTableId, String payMode) {
        //如果在这里使用事务的话，需要使用ThreadLocal来解决，框架中比如mybatis提供了事务支持
        //1.修改bill表
        int update = billDAO.update("update bill set state = ? where diningTableId = ? and state = '未结账'", payMode, diningTableId);
        if (update <= 0) {//如果更新没有成功，则表示失败...
            return false;
        }
        //2.修改DiningTable表格
        //注意：不要直接在这里操作，而应该调用DiningTableService方法
        if (!diningTableService.updateDiningTableToFree(diningTableId, "空")) {
            return false;
        }
        return true;
    }
}
