package com.hustcaid.myshoppingmanagement.util;

import com.hustcaid.myshoppingmanagement.entity.CartItemVO;
import com.hustcaid.myshoppingmanagement.entity.GoodSale;
import com.hustcaid.myshoppingmanagement.entity.Saleman;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class Util {

    public static List<GoodSale> convertCartItem2GoodSale(List<CartItemVO> cartItemVOList, Saleman sm, LocalDate date) {
        if (cartItemVOList == null || sm == null || date == null) return null;
        ArrayList<GoodSale> goodSales = new ArrayList<>();
        for (CartItemVO item : cartItemVOList) {
            goodSales.add(
                    new GoodSale(0, item.getGoodId(), sm.getSid(), date, item.getAmount()));
        }
        return goodSales;
    }
}
