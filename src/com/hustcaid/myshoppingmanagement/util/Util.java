package com.hustcaid.myshoppingmanagement.util;

import com.hustcaid.myshoppingmanagement.entity.CartItem;
import com.hustcaid.myshoppingmanagement.entity.Good;
import com.hustcaid.myshoppingmanagement.entity.GoodSale;
import com.hustcaid.myshoppingmanagement.entity.Saleman;

import java.time.LocalDate;
import java.util.*;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class Util {

    public static List<GoodSale> convertCartItem2GoodSale(List<CartItem> cartItemList, Saleman sm, LocalDate date) {
        if (cartItemList == null || sm == null || date == null) return null;
        ArrayList<GoodSale> goodSales = new ArrayList<>();
        for (CartItem item : cartItemList) {
            goodSales.add(
                    new GoodSale(0, item.getGid(), sm.getSID(), date, item.getAmount()));
        }
        return goodSales;
    }
}
