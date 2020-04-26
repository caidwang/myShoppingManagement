package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.entity.GoodSale;
import com.hustcaid.myshoppingmanagement.entity.GoodSaleVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/4/23   
 *
 ******************************************************************************/
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
class IGoodSaleDaoTest {
    @Autowired
    IGoodSaleDao goodSaleDao;

    @Test
    void addGoodSale() {
        GoodSale gs = new GoodSale(0, 2, 1, LocalDate.now(), 1);
        goodSaleDao.addGoodSale(gs);
        assertNotEquals(0, gs.getGSID());
    }

    @Test
    void getByDate() {
        // mysql和localDate协同时遇到的坑, mysql默认的时区是CST-usa的,UTC-6, LocalDate的时区是UTC+8, 按照数据库显示的日期是不一定拿得到当天的数据的
        // 解决方法 这里是修改了mysql的时区设置
        List<GoodSaleVO> list = goodSaleDao.getByDate(LocalDate.of(2020, 4, 24));
        assertEquals(2, list.size());
    }
}