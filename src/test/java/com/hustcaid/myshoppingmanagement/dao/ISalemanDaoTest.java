package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.entity.Saleman;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/4/24   
 *
 ******************************************************************************/
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:testApplicationContext.xml")
class ISalemanDaoTest {
    @Autowired
    private ISalemanDao salemanDao;

    @Test
    void add() {
        Saleman sm = new Saleman(0, "111", "wang");
        assertEquals(1, salemanDao.add(sm));
    }

    @Test
    void modify() {
        Saleman sm = new Saleman(1, "1234", "dingding");
        assertEquals(1, salemanDao.modify(sm));
        sm = salemanDao.getBySName("dingding");
        assertEquals("1234", sm.getSpassword());
    }

    @Test
    void delete() {
        Saleman sm = new Saleman(4, "dido456", "dijia");
        assertEquals(1, salemanDao.deleteById(sm));
        sm.setSid(8);
        assertEquals(0, salemanDao.deleteById(sm));
    }

    @Test
    void getBySName() {
        Saleman sm = salemanDao.getBySName("dangdang");
        assertEquals(2, sm.getSid());
        assertEquals("456", sm.getSpassword());
        assertEquals("dangdang", sm.getSname());
        sm = salemanDao.getBySName("dudu");
        assertNull(sm);
    }

    @Test
    void getAll() {
        List<Saleman> salemanList = salemanDao.getAll();
        System.out.println(salemanList);
    }

    @Test
    void fuzzyGet() {
        List<Saleman> salemen = salemanDao.getByFuzzyName("%d%");
        assertNotEquals(0, salemen.size());
    }
}