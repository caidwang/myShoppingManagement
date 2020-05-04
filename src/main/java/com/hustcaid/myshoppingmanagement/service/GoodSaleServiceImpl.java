package com.hustcaid.myshoppingmanagement.service;

import com.hustcaid.myshoppingmanagement.dao.IGoodSaleDao;
import com.hustcaid.myshoppingmanagement.dao.IGoodsDao;
import com.hustcaid.myshoppingmanagement.entity.GoodSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/28   
 *
 ******************************************************************************/
@Service
public class GoodSaleServiceImpl implements GoodSaleService {
    @Autowired
    private IGoodSaleDao goodSaleDao;
    @Autowired
    private IGoodsDao goodsDao;

    /**
     * @param gsList
     * @throws RuntimeException 当事务失败时, 方法抛出运行时异常引起回滚
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public void transaction(List<GoodSale> gsList) throws RuntimeException {
        if (gsList == null || gsList.size() == 0) return;
        for (GoodSale gs : gsList) {
            boolean success = goodSaleDao.addGoodSale(gs) == 1;
            success = success && (goodsDao.consume(gs.getGID(), gs.getNumToSale()) == 1);
            // 注意Spring事务只在遇到RuntimeException时会回滚
            if (!success) {
                throw new RuntimeException("transaction fail on " + gs);
            }
        }
    }
}
