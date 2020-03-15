package com.hustcaid.myshoppingmanagement.view;

import com.hustcaid.myshoppingmanagement.util.Util;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class MainPage {
    public static void rootPage() {
        while (true) {
            System.out.println("               欢迎使用商超管理系统");
            System.out.println("**************************************************\n");
            System.out.println("                  1. 商品维护                      \n");
            System.out.println("                  2. 前台收银                      \n");
            System.out.println("                  3. 后台管理                      \n");
            System.out.println("**************************************************");
            System.out.print("请选择, 输入数字或按0退出:");
            int choose = Util.scanIntAndCheck(0, 3);
            switch (choose) {
                case 1:
                    goodsMaintainPage();
                    break;
                case 2:
                    cashMaintainPage();
                    break;
                case 3:
                    backMangePage();
                    break;
                case 0:
                    System.out.println("正在退出系统...");
                    System.exit(0);
                    break;
            }
        }
    }

    public static void goodsMaintainPage() {
        GoodPage goodPage = new GoodPage();
        boolean loop = true;
        while (loop) {
            System.out.println("商超管理系统>>商品维护");
            System.out.println("**************************************************\n");
            System.out.println("                  1. 添加商品                      \n");
            System.out.println("                  2. 更改商品                      \n");
            System.out.println("                  3. 删除商品                      \n");
            System.out.println("                  4. 显示所有商品                   \n");
            System.out.println("                  5. 查询商品                      \n");
            System.out.println("**************************************************");
            System.out.print("请选择, 输入数字或按0返回上一级菜单:");
            int choose = Util.scanIntAndCheck(0, 5);
            switch (choose) {
                case 1:
                    goodPage.addGoods();
                    break;
                case 2:
                    goodPage.modifyGoods();
                    break;
                case 3:
                    goodPage.removeGoods();
                    break;
                case 4:
                    goodPage.selectAll();
                    break;
                case 5:
                    goodPage.searchGoods();
                    break;
                case 0:
                    loop = false;
                    break;
            }
        }
    }

    public static void cashMaintainPage() {
        CashPage cashPage = new CashPage();
        System.out.println("               欢迎使用商超管理系统");
        System.out.println("**************************************************\n");
        System.out.println("                  1. 登录系统                      \n");
        System.out.println("                  2. 退出                      \n");
        System.out.println("**************************************************");
        System.out.print("请选择, 输入数字:");
        int choose = Util.scanIntAndCheck(1, 2);
        switch (choose) {
            case 1:
                cashPage.signIn();
                break;
            case 2:
                System.out.println("正在退出系统...");
                System.exit(0);
        }
    }
    public static void backMangePage() {
        GoodSalePage gsPage = new GoodSalePage();
        boolean loop = true;
        while (loop) {
            System.out.println("商超管理系统>>商品管理");
            System.out.println("**************************************************\n");
            System.out.println("                  1. 列出当日卖出商品列表           \n");
            System.out.println("                  2. 售货员管理                      \n");
            System.out.println("**************************************************");
            System.out.print("请选择, 输入数字或按0返回上一级菜单:");
            int choose = Util.scanIntAndCheck(0, 2);
            switch (choose) {
                case 1:
                    gsPage.listSales();
                    break;
                case 2:
                    salemanManagePage();
                    break;
                case 0:
                    loop = false;
                    break;
            }
        }
    }

    public static void salemanManagePage() {
        SalemanPage sPage = new SalemanPage();
        boolean loop = true;
        while (loop) {
            System.out.println("商超管理系统>>商品管理>>售货员管理");
            System.out.println("**************************************************\n");
            System.out.println("                  1. 添加售货员           \n");
            System.out.println("                  2. 更改售货员                      \n");
            System.out.println("                  3. 删除售货员                      \n");
            System.out.println("                  4. 显示所有售货员                      \n");
            System.out.println("                  5. 查询售货员                      \n");
            System.out.println("**************************************************");
            System.out.print("请选择, 输入数字或按0返回上一级菜单:");
            int choose = Util.scanIntAndCheck(0, 5);
            switch (choose) {
                case 1:
                    sPage.add();
                    break;
                case 2:
                    sPage.modify();
                    break;
                case 3:
                    sPage.delete();
                    break;
                case 4:
                    sPage.listAll();
                    break;
                case 5:
                    sPage.search();
                    break;
                case 0:
                    loop = false;
                    break;
            }
        }
    }

    public static void main(String[] args) {
        rootPage();
    }
}
