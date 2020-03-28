package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.entity.Saleman;

import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/28   
 *
 ******************************************************************************/
public interface ISalemanDao {
    boolean add(Saleman sm);

    boolean modify(Saleman sm);

    boolean delete(Saleman saleman);

    Saleman getBySName(String sName);

    List<Saleman> getAll();

    List<Saleman> fuzzyGet(String keyWord);
}
