package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.entity.GoodSale;
import com.hustcaid.myshoppingmanagement.entity.GoodSaleCollection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
@Slf4j
@Repository
public class GoodSaleDao implements IGoodSaleDao {
    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public boolean addGoodSale(GoodSale goodSale) {
        SimpleJdbcInsert gsInsert = new SimpleJdbcInsert(jdbc).withTableName("GSALES").usingGeneratedKeyColumns("GSID");
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("GID", goodSale.getGID());
            put("SID", goodSale.getSID());
            put("SDATE", Date.valueOf(goodSale.getDate()));
            put("SNUM", goodSale.getNumToSale());
        }};
        try {
            int id = gsInsert.executeAndReturnKey(map).intValue();
            goodSale.setGSID(id);
        } catch (DataIntegrityViolationException e) {
            log.warn("GoodSale record insert fail. Foreign key invalid." + goodSale);
            return false;
        }
        return true;
    }

    /**
     * 按照销售日期返回销售记录
     *
     * @param date 待查询的销售日期
     * @return GoodSale的列表, 当date非法或没有销售记录时, 列表为空.
     */
    @Override
    public List<GoodSaleCollection> getByDate(LocalDate date) {
        List<GoodSaleCollection> list;
        list = jdbc.query("SELECT GNAME, GPRICE, GNUM, TOTAL FROM SALELIST WHERE SDATE = ?;", (resultSet, i) -> {
                    String name = resultSet.getString("GNAME");
                    double price = resultSet.getDouble("GPRICE");
                    int gnum = resultSet.getInt("GNUM");
                    int total = resultSet.getInt("TOTAL");
                    return new GoodSaleCollection(name, price, gnum, total);
                },
                Collections.singletonList(Date.valueOf(date)));
        return list;
    }
}
