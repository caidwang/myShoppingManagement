package view;

import dao.GoodSaleDao;
import dao.goodsDao;
import dao.salemanDao;
import entity.Good;
import entity.GoodSale;
import entity.Saleman;
import util.util;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class CashPage {
    private static class CashEntry extends AbstractEntry {
        private Good good;
        private Saleman sm;
        private int numOfBuy;
        private double totalPrice;
        public CashEntry(Good good, Saleman sm, int num) {
            this.good = good;
            this.sm = sm;
            this.numOfBuy = num;
            this.totalPrice = good.getGPrice() * num;
        }
        @Override
        public void printHead() {

        }
        @Override
        public void printEntry() {
            System.out.println(good.getGName()+ "\t" + good.getGPrice() + "\t" + numOfBuy + "\t" + totalPrice);
        }

        public GoodSale convertToGS() {
            return new GoodSale(sm, good, LocalDate.now(), numOfBuy);
        }

        public static double cumulateTotal(List<CashEntry> ceList) {
            double sum = 0;
            if (ceList == null) return sum;
            for (CashEntry ce: ceList) sum += ce.totalPrice;
            return sum;
        }
    }

    public void signIn() {
        int loop = 3;
        boolean login = false;
        Scanner in = new Scanner(System.in);
        Saleman sm = null;
        while (loop-- > 0) {
            System.out.print("请输入用户名:");
            String name = in.next();
            sm = salemanDao.isExists(name);
            if (sm != null) break; // 如果有这个销售员验证密码
            else System.out.println("用户名错误.剩余" + loop + "次机会.");
        }
        if (sm != null ) loop = 3; // 如果不存在, 后面的输入密码也不用进行
        while (loop-- > 0) {
            System.out.print("请输入密码");
            String pswd = in.next();
            if (pswd.equals(sm.getPasswd())) {
                login = true;
                break;
            }
            else System.out.println("密码错误.剩余" + loop + "次机会.");
        }
        if (login) {
            cashing(sm);
        }
        else {
            System.out.println("正在退出系统...");
            System.exit(0);
        }
    }
    private void cashing(Saleman sm) {
        Scanner in = new Scanner(System.in);
        boolean loop = true;
        List<CashEntry> shoppingList = new LinkedList<>();
        System.out.println("\t\t1.购物结算\n");
        while (loop) {
            shoppingList.clear();
            do {
                System.out.println("输入商品关键字:");
                String keyword = in.next() + "%";
                List<Good> list = goodsDao.fuzzyGet(keyword);
                util.printGoodsFromList(list, "");
                if (list.size() == 0) {
                    System.out.println("没有匹配的商品");
                    continue;
                }
                System.out.print("请选择商品:");
                String gName = in.next();
                for (Good g: list) {
                    if (gName.equals(g.getGName())) {
                        System.out.print("请输入购买数量:");
                        int sNum = util.scanIntAndCheck(1, g.getGNum());
                        shoppingList.add(new CashEntry(g, sm, sNum));
                        CashEntry.printList(shoppingList);
                        break;
                    }
                }
            } while (util.checkEndOrContinue());
            double sum = CashEntry.cumulateTotal(shoppingList);
            System.out.println("总计: " + sum);
            System.out.println("请输入实际缴费金额:");
            double money = in.nextDouble();
            System.out.println("找钱:" + (money - sum));
            if (executeTransaction(shoppingList)) System.out.println("交易成功.");
            else System.out.println("交易失败.");
            loop = util.checkEndOrContinue();
        }
        System.out.println("正在退出系统...");
        System.exit(0);
    }
    private boolean executeTransaction(List<CashEntry> CEList) {
        if (CEList == null) return false;
        if (CEList.size() == 0) return true;
        List<GoodSale> GSList = new LinkedList<>();
        for (CashEntry ce: CEList) {
            GSList.add(ce.convertToGS());
        }
        return GoodSaleDao.create(GSList);
    }
}
