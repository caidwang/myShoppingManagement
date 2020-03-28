package com.hustcaid.myshoppingmanagement.service;

import com.hustcaid.myshoppingmanagement.dao.GoodSaleDao;
import com.hustcaid.myshoppingmanagement.dao.GoodsDao;
import com.hustcaid.myshoppingmanagement.entity.GoodSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/28   
 *
 ******************************************************************************/
@Service
public class GoodSaleServiceImpl implements GoodSaleService {
    @Autowired
    private GoodSaleDao goodSaleDao;
    @Autowired
    private GoodsDao goodsDao;

    @Override
    public boolean transaction(List<GoodSale> gsList) {
        if (gsList == null || gsList.size() == 0) return false;
        boolean success = true;
        for (GoodSale gs : gsList) {
            success = success && goodSaleDao.addGoodSale(gs);
            success = success && goodsDao.consume(gs.getGID(), gs.getNumToSale());
            if (!success) break;
        }
        return success;
    }
}
