package com.hustcaid.myshoppingmanagement.service;

import com.hustcaid.myshoppingmanagement.entity.GoodSale;

import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/28   
 *
 ******************************************************************************/
public interface GoodSaleService {
    boolean transaction(List<GoodSale> gsList);
}
