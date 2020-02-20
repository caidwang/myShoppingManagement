package view;

import dao.GoodSaleDao;
import util.util;

import java.time.LocalDate;
import java.util.List;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class GoodSalePage {
    public static class GSEntry extends AbstractEntry {
        private String name;
        private double price;
        private int numPreserve;
        private int numSaled;

        public GSEntry(String name, double price, int numPreserve, int numSaled) {
            this.name = name;
            this.price = price;
            this.numPreserve = numPreserve;
            this.numSaled = numSaled;
        }
        @Override
        public void printHead() {
            System.out.println("商品名称\t商品价格\t商品数量\t销量\t备注");
        }

        @Override
        public void printEntry() {
            System.out.println(name + "\t" + price + "\t"
                    + numPreserve + "\t" + numSaled + "\t"
                    + (numPreserve < 10 ? "*该商品不足十件":""));
        }
    }
    public void listSales() {
        do {
            List<GSEntry> list = GoodSaleDao.getByDate(LocalDate.now());
            if (list != null && list.size() > 0) {
                AbstractEntry.printList(list);
            }
        } while (util.checkEndOrContinue());
    }
}
