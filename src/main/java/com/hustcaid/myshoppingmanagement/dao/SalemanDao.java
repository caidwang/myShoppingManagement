package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.entity.Saleman;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

/*
   todo: 完善各种Dao操作的Exception捕获流程, 明确哪些异常是可以直接返回false的, 哪些异常
   需要直接往上抛, 以GoodSaleDao.addGoodSale为例
   调整dao的api, 返回状态码, 按照状态码判断执行是否成功并提供信息供前端提醒
 */

@Slf4j
@Repository
public class SalemanDao implements ISalemanDao {
    private final JdbcTemplate jdbc;
    private SimpleJdbcInsert salemanInsert;

    @Autowired
    public SalemanDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        salemanInsert = new SimpleJdbcInsert(jdbc).withTableName("SALESMAN").usingGeneratedKeyColumns("SID");
    }

    /**
     * 向数据库添加saleman记录
     *
     * @param sm 待添加Saleman对象, 要求sName和sPassword属性不为空
     * @return 如果操作成功, 返回true,并设置sm对象的ID, 否则返回false.
     */
    @Override
    public boolean add(Saleman sm) {
        if (sm == null || sm.getSName() == null || sm.getSPassword() == null) {
            log.info("add Saleman fail " + sm);
            return false;
        }
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("SNAME", sm.getSName());
            put("SPASSWORD", sm.getSPassword());
        }};
        try {
            int id = salemanInsert.executeAndReturnKey(map).intValue();
            sm.setSID(id);
        } catch (DuplicateKeyException e) {
            log.warn("SaleMan add Fail: " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 按照给定的saleman对象的属性值修改数据库中该对象的@code{columnName}属性
     *
     * @param sm 已经修改好的saleman对象
     * @return 如果操作成功返回true, 否则返回false
     */
    @Override
    public boolean modify(Saleman sm) {
        if (sm == null || sm.getSName() == null || sm.getSPassword() == null) return false;
        int ok = jdbc.update("UPDATE SALESMAN SET SNAME=?, SPASSWORD=? WHERE SID=?;",
                sm.getSName(), sm.getSPassword(), sm.getSID());
        return ok == 1;
    }

    /**
     * 按照id将给定的saleman对象在数据库中的记录删除
     *
     * @param saleman 给定的saleman对象
     * @return 如果操作成功返回true, 否则返回false
     */
    @Override
    public boolean delete(Saleman saleman) {
        if (saleman == null) {
            return false;
        }
        int ok;
        try {
            ok = jdbc.update("DELETE FROM SALESMAN where SID = ?;", saleman.getSID());
        } catch (DataAccessException e) {
            log.warn("Saleman delete fail: " + e.getMessage());
            return false;
        }
        log.info("delete saleman " + saleman + (ok == 1 ? " ok" : " fail"));
        return ok == 1;
    }

    /**
     * 按照SName查询销售员
     *
     * @param sName
     * @return
     */
    @Override
    public Saleman getBySName(String sName) {
        if (sName == null) {
            return null;
        }
        return jdbc.queryForObject("SELECT * FROM SALESMAN where SNAME = ? LIMIT 1;", new SaleManMapper(), sName);
    }

    /**
     * 返回所有的售货员成员
     *
     * @return 所有的售货员列表
     */
    @Override
    public List<Saleman> getAll() {
        return jdbc.query("select SID, SNAME, SPASSWORD FROM SALESMAN;", new SaleManMapper());
    }

    /**
     * 按照模糊搜索查找销售员
     *
     * @param keyWord 模糊搜索键值, 形如"张%"
     * @return
     */
    @Override
    public List<Saleman> fuzzyGet(String keyWord) {
        if (keyWord == null) return new ArrayList<Saleman>();
        return jdbc.query("SELECT * FROM SALESMAN WHERE SNAME LIKE ?;", new SaleManMapper(), keyWord);
    }

    private static class SaleManMapper implements RowMapper<Saleman> {
        /**
         * Implementations must implement this method to map each row of data
         * in the ResultSet. This method should not call {@code next()} on
         * the ResultSet; it is only supposed to map values of the current row.
         *
         * @param rs     the ResultSet to map (pre-initialized for the current row)
         * @param rowNum the number of the current row
         * @return the result object for the current row (may be {@code null})
         * @throws SQLException if an SQLException is encountered getting
         *                      column values (that is, there's no need to catch SQLException)
         */
        @Override
        public Saleman mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("SID");
            String name = rs.getString("SNAME");
            String passwd = rs.getString("SPASSWORD");
            return new Saleman(id, name, passwd);
        }
    }

}
