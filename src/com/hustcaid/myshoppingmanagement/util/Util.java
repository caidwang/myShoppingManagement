package com.hustcaid.myshoppingmanagement.util;

import com.hustcaid.myshoppingmanagement.entity.Good;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class Util {
    public static int scanIntAndCheck(int start, int end) {
        while (true) {
            Scanner in = new Scanner(System.in);
            try {
                int choose = in.nextInt();
                if (choose >= start && choose <= end) {
                    return choose;
                }
                System.out.print("非法输入! 请重新输入:");
            }
            catch (InputMismatchException e) {
                System.out.print("非法输入! 请重新输入:");
            }

        }
    }
    public static boolean checkYN() {
        while (true) {
            Scanner in = new Scanner(System.in);
            String choose = in.next();
            if ("y".equals(choose) || "Y".equals(choose)) {
                return true;
            } else if ("n".equals(choose) || "N".equals(choose)) {
                return false;
            } else {
                System.out.println("非法输入, 请重新输入: ");
            }
        }
    }

    public static boolean checkEndOrContinue() {
        System.out.print("是否继续?(y/n)");
        return checkYN();
    }

    public static int scanIntAndCheck() {
        return scanIntAndCheck(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void printGoodsFromList(List<Good> list, String pattern) {
        boolean note = false;
        switch (pattern) {
            case "notes":
                note = true;
                break;
            case "priceIncr":
                list.sort((Good a, Good b) -> {
                    return a.getGPrice() < b.getGPrice() ? -1 : 1;
                });
                break;
            case "numIncr":
                list.sort(Comparator.comparingInt(Good::getGNum));
                break;
            default:
        }
        System.out.println("商品名称\t商品价格\t商品数量\t备注");
        for(Good g : list) {
            System.out.print(g.getGName() + "\t" + g.getGPrice() + "\t" + g.getGNum() + "\t");
            if (note && g.getGNum() < 10) {
                System.out.println("*该商品不足10件");
            }
            System.out.println();
        }
    }
}
