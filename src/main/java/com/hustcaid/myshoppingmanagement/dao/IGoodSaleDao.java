package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.entity.GoodSale;
import com.hustcaid.myshoppingmanagement.entity.GoodSaleCollection;

import java.time.LocalDate;
import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/28   
 *
 ******************************************************************************/
public interface IGoodSaleDao {
    boolean addGoodSale(GoodSale goodSale);

    List<GoodSaleCollection> getByDate(LocalDate date);
}
