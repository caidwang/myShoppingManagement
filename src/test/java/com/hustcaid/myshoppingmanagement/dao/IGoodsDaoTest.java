package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.entity.Good;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/4/24   
 *
 ******************************************************************************/
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:testApplicationContext.xml")
class IGoodsDaoTest {
    @Autowired
    private IGoodsDao goodsDao;

    @Test
    void add() {
        Good good = new Good("testGood", 2.5, 5);
        int add = goodsDao.add(good);
        assertEquals(1, add);
    }

    @Test
    void update() {
        Good good = new Good(3, "goodThree", 15.0, 20);
        int update = goodsDao.update(good);
        assertEquals(1, update);
    }

    @Test
    void consume() {
        int valid = goodsDao.consume(2, 1);
        assertEquals(1, valid);
        // 测试超卖
        int invalid = goodsDao.consume(2, 100);
        assertEquals(0, invalid);
    }

    @Test
    void delete() {
        Good good = new Good(1, "good1", 15, 1);
        assertEquals(1, goodsDao.delete(good));
    }

    @Test
    void getByGName() {
        Good good = goodsDao.getByGName("coco");
        assertEquals(2, good.getGId());

        good = goodsDao.getByGName("xiaoxiao");
        assertNull(good);
    }

    @Test
    void getAll() {
        List<Good> goods = goodsDao.getAll();
        System.out.println(goods);
    }

    @Test
    void getByFuzzyName() {
        List<Good> goods = goodsDao.getByFuzzyName("%co%");
        assertEquals(1, goods.size());
    }
}