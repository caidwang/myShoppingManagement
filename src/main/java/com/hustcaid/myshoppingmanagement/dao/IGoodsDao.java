package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.entity.Good;

import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/28   
 *
 ******************************************************************************/
public interface IGoodsDao {
    boolean add(Good good);

    boolean modify(Good good);

    boolean consume(Good good, int num);

    boolean consume(int gid, int num);

    boolean delete(Good good);

    Good getByGName(String GName);

    List<Good> getAll();

    List<Good> fuzzyGet(String nameSeg);
}
