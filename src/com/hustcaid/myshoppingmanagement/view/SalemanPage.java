package com.hustcaid.myshoppingmanagement.view;

import com.hustcaid.myshoppingmanagement.dao.SalemanDao;
import com.hustcaid.myshoppingmanagement.entity.Saleman;
import com.hustcaid.myshoppingmanagement.util.Util;

import java.util.List;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class SalemanPage {
    public void add() {
        boolean loop = true;
        while (loop) {
            System.out.println("执行添加售货员操作:\n");
            System.out.println("添加售货员姓名");
            Scanner in = new Scanner(System.in);
            String name = in.next();
            if (SalemanDao.isExists(name) != null) {
                System.out.println("售货员已存在.");
            }
            else {
                System.out.println("请输入售货员密码:");
                String passwd = in.next();

                if (SalemanDao.add(new Saleman(name, passwd))) {
                    System.out.println("添加成功.");
                } else {
                    System.out.println("添加失败");
                }
            }
            loop = Util.checkEndOrContinue();
        }
    }
    public void modify() {
        boolean loop = true;
        while (loop) {
            System.out.println("执行更改售货员信息操作:\n");
            System.out.println("输入更改售货员名称");
            Scanner in = new Scanner(System.in);
            String name = in.next();
            Saleman sm = SalemanDao.isExists(name);
            if (sm == null) {
                System.out.println("该售货员不存在.");
            }
            else {
                System.out.println("售货员姓名\t售货员密码");
                System.out.println(sm.getSName() + "\t" + sm.getPasswd() + "\n");
                System.out.println("选择您要更改的内容");
                System.out.println("1. 更改售货员姓名:\n" +
                        "2. 更改售货员密码:");
                int choose = Util.scanIntAndCheck(1, 2);
                boolean success = false;
                switch (choose) {
                    case 1:
                        System.out.println("请输入已更改名称");
                        sm.setSName(in.next());
                        success = SalemanDao.modify(sm, "SNAME");
                        break;
                    case 2:
                        System.out.println("请输入已更改密码");
                        sm.setPasswd(in.next());
                        success = SalemanDao.modify(sm, "SPASSWORD");

                }
                if (success) {
                    System.out.println("更改成功.");
                } else {
                    System.out.println("更改失败.");
                }

            }
            loop = Util.checkEndOrContinue();
        }
    }
    public void delete() {
        boolean loop = true;
        while (loop) {
            System.out.println("执行删除售货员操作:\n");
            System.out.println("输入待删除售货员姓名");
            Scanner in = new Scanner(System.in);
            String name = in.next();
            Saleman sm = SalemanDao.isExists(name);
            if (sm == null) {
                System.out.println("该商品不存在.");
            }
            else {
                System.out.println("售货员姓名\t售货员密码");
                System.out.println(sm.getSName() + "\t" + sm.getPasswd() + "\n");
                System.out.println("是否确定要删除(y/n)?");
                if (Util.checkYN()) {
                    SalemanDao.delete(sm);
                }
            }
            loop = Util.checkEndOrContinue();
        }
    }
    public void listAll() {
        boolean loop = true;
        while (loop) {
            System.out.println("显示所有售货员:\n");
            List<Saleman> list = SalemanDao.getAll();
            printAllSaleman(list);
            loop = Util.checkEndOrContinue();
        }
    }
    public void search() {
        boolean loop = true;
        while (loop) {
            System.out.println("执行查询售货员操作:\n");
            System.out.println("请输入关键字");
            Scanner in = new Scanner(System.in);
            String keyword = in.next() + "%";
            List<Saleman> list = SalemanDao.fuzzyGet(keyword);
            printAllSaleman(list);
            loop = Util.checkEndOrContinue();
        }
    }
    private void printAllSaleman(List<Saleman> list) {
        System.out.println("售货员姓名\t售货员密码");
        for (Saleman sm : list) {
            System.out.println(sm.getSName() + "\t" + sm.getPasswd());
        }
    }
}
