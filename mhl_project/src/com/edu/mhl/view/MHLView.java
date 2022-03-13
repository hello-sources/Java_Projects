package com.edu.mhl.view;

import com.edu.mhl.domain.Bill;
import com.edu.mhl.domain.DiningTable;
import com.edu.mhl.domain.Employee;
import com.edu.mhl.domain.Menu;
import com.edu.mhl.service.BillService;
import com.edu.mhl.service.DiningTableService;
import com.edu.mhl.service.EmployeeService;
import com.edu.mhl.service.MenuService;
import com.edu.mhl.utils.Utility;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.List;

/**
 * 这是一个主界面
 * */
public class MHLView {
    //控制是否退出菜单
    private boolean loop = true;
    private String key = "";//接受用户的选择
    //定义EmployeeService属性
    private EmployeeService employeeService = new EmployeeService();
    //调用DiningTableService的属性
    private DiningTableService diningTableService = new DiningTableService();
    //定义MenuService属性
    private MenuService menuService = new MenuService();
    //定义BillService属性
    private BillService billService = new BillService();

    public static void main(String[] args) {
        new MHLView().mainMenu();
    }

    //完成结账
    public void payBill() {
        System.out.println("================结账服务================");
        System.out.print("请选择要结账的餐桌编号(-1退出)：");
        System.out.print("结账方式(现金/支付宝/微信)回车表示退出：");
        int diningTableId = Utility.readInt();
        if (diningTableId == -1) {
            System.out.println("================取消结账================");
            return ;
        }
        //验证餐桌是否存在
        DiningTable diningTable = diningTableService.getDiningTableById(diningTableId);
        if (diningTable == null) {
            System.out.println("================结账的餐桌不存在================");
            return ;
        }
        //验证餐桌是否有未结账的账单
        if (!billService.hasPayBillByDiningTableId(diningTableId)) {
            System.out.println("================该餐位没有未结账账单================");
            return ;
        }
        System.out.print("结账方式(现金/支付宝/微信)回车表示退出：");
        String payMode = Utility.readString(20, ""); //说明如果回车,就是返回""
        if ("".equals(payMode)) {
            System.out.println("================取消结账================");
            return ;
        }
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {//结账
            //调用写的billService中的方法payBill
            if (billService.payBill(diningTableId, payMode)) {
                System.out.println("================完成结账================");
            } else {
                System.out.println("================结账失败================");
            }
        } else {
            System.out.println("================取消结账================");
        }
    }

    //显示账单信息
    public void listBill() {
        List<Bill> bills = billService.list();
        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态");
        for (Bill bill : bills) {
            System.out.println(bill);
        }
        System.out.println("================显示完毕================");
    }

    //完成点餐
    public void orderMenu() {
        System.out.println("================点餐服务================");
        System.out.print("请输入点餐桌号(-1退出)：");
        int orderDiningTableId = Utility.readInt();
        if (orderDiningTableId == -1) {
            System.out.println("================取消点餐================");
            return ;
        }
        System.out.print("请输入点餐的菜品编号(-1退出)：");
        int orderMenuId = Utility.readInt();
        if (orderMenuId == -1) {
            System.out.println("================取消点餐================");
            return ;
        }
        System.out.print("请输入点餐的菜品量(-1退出)：");
        int orderNums = Utility.readInt();
        if (orderNums == -1) {
            System.out.println("================取消点餐================");
            return ;
        }

        //验证餐桌号是否存在
        DiningTable diningTable = diningTableService.getDiningTableById(orderDiningTableId);
        if (diningTable == null) {
            System.out.println("================餐桌号不存在================");
            return ;
        }

        //验证菜品编号
        Menu menu = menuService.getMenuById(orderMenuId);
        if (menu == null) {
            System.out.println("================菜品号不存在================");
            return ;
        }

        //点餐
        if (billService.orderMenu(orderMenuId, orderNums, orderDiningTableId)) {
            System.out.println("================点餐成功================");
        } else {
            System.out.println("================点餐失败================");
        }
    }

    //显示所有菜品
    public void listMenu() {
        List<Menu> list = menuService.list();
        System.out.println("\n菜品编号\t\t菜品名\t\t类别\t\t价格");
        for (Menu menu : list) {
            System.out.println(menu);
        }
        System.out.println("================显示完毕================");
    }

    //完成订座
    public void orderDingingTable() {
        System.out.println("================预定餐桌================");
        System.out.print("请选择要预定的餐桌编号(-1退出)：");
        int orderId = Utility.readInt();
        if (orderId == -1) {
            System.out.println("================取消预定餐桌================");
            return ;
        }
        //该方法得到的是Y或N
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {//要预定
            //根据orderId返回对DiningTable对象，如果为null，则说明该对象不存在
            DiningTable diningTable = diningTableService.getDiningTableById(orderId);
            if (diningTable == null) {
                System.out.println("================预定餐桌不存在================");
                return ;
            }
            //判断该餐桌状态是否为非空状态
            if (!("空".equals(diningTable.getState()))) {
                System.out.println("================该餐桌已经预定或者在就餐中================");
                return ;
            }
            //接受预定信息
            System.out.print("预定人的名字：");
            String orderName = Utility.readString(50);
            System.out.print("预定人的电话：");
            String orderTel = Utility.readString(50);
            //这时候说明真的可以预定，更新餐桌状态
            if (diningTableService.orderDiningTable(orderId, orderName, orderTel)) {
                System.out.println("================预定餐桌成功================");
            } else {
                System.out.println("================预定餐桌失败================");
            }
        } else {
            System.out.println("================取消预定餐桌================");
        }
    }

    //显示所有餐桌
    public void listDiningTable() {
        List<DiningTable> list = diningTableService.list();
        System.out.println("\n餐桌编号\t\t餐桌状态");
        for (DiningTable diningTable : list) {
            System.out.println(diningTable);
        }
        System.out.println("================显示完毕================");
    }

    //显示主菜单
    public void mainMenu() {
        while (loop) {
            System.out.println("=================满汉楼================");
            System.out.println("\t\t 1.登录满汉楼");
            System.out.println("\t\t 2.推出满汉楼");
            System.out.println("请输入你的选择：");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.println("请输入员工号：");
                    String empid = Utility.readString(50);
                    System.out.println("请输入密  码：");
                    String pwd = Utility.readString(50);
                    Employee employee = employeeService.getEmployeeByIdAndPwd(empid, pwd);
                    //到数据库去判断
                    if (employee != null) {
                        System.out.println("================登录成功[" + employee.getName() + "]================\n");
                        //显示二级菜单,二级菜单是循环操作，所以做成while
                        while (loop) {
                            System.out.println("\n================满汉楼(二级菜单)================");
                            System.out.println("\t\t 1 显示餐桌状态");
                            System.out.println("\t\t 2 预定餐桌");
                            System.out.println("\t\t 3 显示所有菜品");
                            System.out.println("\t\t 4 点餐服务");
                            System.out.println("\t\t 5 查看账单");
                            System.out.println("\t\t 6 结账");
                            System.out.println("\t\t 9 退出满汉楼");
                            System.out.println("请输入您的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    listDiningTable();//显示餐桌状态
                                    break;
                                case "2":
                                    orderDingingTable();
                                    break;
                                case "3":
                                    listMenu();
                                    break;
                                case "4":
                                    orderMenu();
                                    break;
                                case "5":
                                    listBill();//显示所有账单
                                    break;
                                case "6":
                                    payBill();
                                    break;
                                case "9":
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("你的输入有误，请重新输入");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("================登录失败================");
                    }
                    break;
                case "2":
                    loop = false;
                    break;
                default:
                    System.out.println("你输入有误，请重新输入！");
            }
        }
        System.out.println("你退出满汉楼系统");
    }
}
