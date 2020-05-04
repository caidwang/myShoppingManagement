package com.hustcaid.myshoppingmanagement.service;

import com.hustcaid.myshoppingmanagement.dao.IGoodSaleDao;
import com.hustcaid.myshoppingmanagement.dao.IGoodsDao;
import com.hustcaid.myshoppingmanagement.entity.GoodSale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/4/24   
 *
 ******************************************************************************/
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
class GoodSaleServiceTest {
    @Mock
    private IGoodSaleDao goodSaleDao;

    @Mock
    private IGoodsDao goodsDao;

    @InjectMocks
    private GoodSaleServiceImpl goodSaleService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void transactionValid() {
        List<GoodSale> list = new ArrayList<>();
        GoodSale goodSale1 = new GoodSale(0, 2, 1, LocalDate.now(), 3);
        list.add(goodSale1);

        when(goodsDao.consume(2, 3)).thenReturn(1);
        when(goodSaleDao.addGoodSale(goodSale1)).thenReturn(1);
        assertDoesNotThrow(() -> goodSaleService.transaction(list));
    }

    @Test
    void transactionInvalid() {
        List<GoodSale> list = new ArrayList<>();
        GoodSale goodSale1 = new GoodSale(0, 2, 1, LocalDate.now(), 3);
        GoodSale goodSale2 = new GoodSale(0, 3, 1, LocalDate.now(), 100);
        list.add(goodSale1);
        list.add(goodSale2);

        when(goodsDao.consume(2, 1)).thenReturn(1);
        when(goodsDao.consume(3, 100)).thenReturn(0);
        when(goodSaleDao.addGoodSale(goodSale1)).thenReturn(1);
        when(goodSaleDao.addGoodSale(goodSale2)).thenReturn(1);

        assertThrows(RuntimeException.class, () -> goodSaleService.transaction(list));
    }
}