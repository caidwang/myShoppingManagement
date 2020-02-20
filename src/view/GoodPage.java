package view;

import dao.goodsDao;
import entity.Good;
import util.util;


import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class GoodPage {
    public void addGoods() {
        boolean loop = true;
        while (loop) {
            System.out.println("执行添加商品操作:\n");
            System.out.println("添加商品名称");
            Scanner in = new Scanner(System.in);
            String name = in.next();
            if (goodsDao.isExists(name) != null) {
                System.out.println("商品已存在.");
            }
            else {
                System.out.println("添加商品价格:");
                double price = in.nextDouble();
                System.out.println("添加商品数量: ");
                int num = in.nextInt();
                if (goodsDao.add(new Good(name, price, num))) {
                    System.out.println("添加成功.");
                } else System.out.println("添加失败");
            }
            loop = util.checkEndOrContinue();
        }
    }
    public void modifyGoods() {
        boolean loop = true;
        while (loop) {
            System.out.println("执行更改商品操作:\n");
            System.out.println("输入更改商品名称");
            Scanner in = new Scanner(System.in);
            String name = in.next();
            Good good = goodsDao.isExists(name);
            if (good == null) {
                System.out.println("该商品不存在.");
            }
            else {
                System.out.println("商品名称\t商品价格\t商品数量");
                System.out.println(good.getGName() + "\t" + good.getGPrice() + "\t" + good.getGNum() + "\n");
                System.out.println("选择您要更改的内容");
                System.out.println("1. 更改商品名称:\n" +
                         "2. 更改商品价格:\n" +
                         "3. 更改商品数量:");
                int choose = util.scanIntAndCheck(1, 3);
                boolean success = false;
                switch (choose) {
                    case 1:
                        System.out.println("请输入已更改名称");
                        good.setGName(in.next());
                        success = goodsDao.modify(good, "GNAME");
                        break;
                    case 2:
                        System.out.println("请输入已更改价格");
                        good.setGPrice(in.nextDouble());
                        success = goodsDao.modify(good, "GPRICE");
                        break;
                    case 3:
                        System.out.println("请输入已更改数量");
                        int num = util.scanIntAndCheck(0, Integer.MAX_VALUE);
                        success = goodsDao.modify(good, "GNUM");
                }
                if (success) System.out.println("更改成功.");
                else System.out.println("更改失败.");

            }
            loop = util.checkEndOrContinue();
        }
    }
    public void removeGoods() {
        boolean loop = true;
        while (loop) {
            System.out.println("执行删除商品操作:\n");
            System.out.println("输入待删除商品名称");
            Scanner in = new Scanner(System.in);
            String name = in.next();
            Good good = goodsDao.isExists(name);
            if (good == null) {
                System.out.println("该商品不存在.");
            }
            else {
                System.out.println("商品名称\t商品价格\t商品数量");
                System.out.println(good.getGName() + "\t" + good.getGPrice() + "\t" + good.getGNum() + "\n");
                System.out.println("是否确定要删除(y/n)?");
                if (util.checkYN()) {
                    goodsDao.delete(good);
                }
            }
            loop = util.checkEndOrContinue();
        }
    }
    public void selectAll() {
        boolean loop = true;
        while (loop) {
            System.out.println("显示所有商品:\n");
            List<Good> list = goodsDao.getAll();
            util.printGoodsFromList(list, "notes");
            loop = util.checkEndOrContinue();
        }
    }
    public void searchGoods() {
        boolean loop = true;
        while (loop) {
            System.out.println("执行查询商品操作:\n");
            System.out.println("1. 按商品数量升序查询\n2. 按商品价格升序查询\n3. 输入关键字查询商品");
            System.out.println("请选择, 输入数字或按0返回上级菜单");
            int choose = util.scanIntAndCheck(0, 3);
            switch (choose) {
                case 1:
                    List<Good> list = goodsDao.getAll();
                    util.printGoodsFromList(list, "numIncr");
                    break;
                case 2:
                    List<Good> list2 = goodsDao.getAll();
                    util.printGoodsFromList(list2, "priceIncr");
                    break;
                case 3:
                    System.out.println("请输入关键字");
                    Scanner in = new Scanner(System.in);
                    String keyword = in.next() + "%";
                    List<Good> list3 = goodsDao.fuzzyGet(keyword);
                    util.printGoodsFromList(list3, "");
                    break;
                case 0:
                    loop = false;
            }
        }
    }

}
