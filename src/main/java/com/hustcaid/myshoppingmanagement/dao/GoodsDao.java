package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.entity.Good;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Objects;


/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
@Slf4j
@Repository
public class GoodsDao {
    @Autowired
    private JdbcTemplate jdbc;

    /**
     * 向数据库添加商品项, 并设置商品ID. 当
     * 商品@code{good}的GName为null,价格为0, 商品数量小于0时, 认为是一个无效的@code{good}
     * 对象. 如果商品名已存在, 插入失败.
     *
     * @param good
     * @return 当执行成功时返回true, 否则返回false
     */
    public boolean add(Good good) {
        if (good == null || good.getGName() == null || good.getGPrice() == 0 || good.getGNum() < 0) {
            log.info("add Good fail." + good);
            return false;
        }
        PreparedStatementCreatorFactory preparedStatementCreatorFactory
                = new PreparedStatementCreatorFactory("INSERT INTO GOODS (GNAME, GPRICE, GNUM) VALUES (?,?,?);",
                Types.VARCHAR, Types.DECIMAL, Types.DECIMAL);
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = preparedStatementCreatorFactory.newPreparedStatementCreator(new Object[]{
                good.getGName(),
                good.getGPrice(),
                good.getGNum()
        });
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int ok = jdbc.update(psc, keyHolder);
        if (ok == 0) {
            log.info("add Good fail." + good);
            return false;
        }
        good.setGId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return true;
    }

    /**
     * 修改Good的商品属性
     *
     * @param good, 待修改的商品对象, 其中属性修改
     * @return 如果修改成功返回@code{true}, 修改失败返回@code{false}
     */
    public boolean modify(Good good) {
        if (good == null || good.getGPrice() < 0 || good.getGNum() < 0) {
            log.info("add Good fail." + good);
            return false;
        }
        int ok = jdbc.update("UPDATE GOODS SET GNAME=?,GPRICE=?,GNUM=? WHERE GID=?;",
                good.getGName(), good.getGPrice(), good.getGNum(), good.getGId());
        return ok == 1;
    }

    public boolean consume(Good good, int num) {
        if (good == null) return false;
        return consume(good.getGId(), num);
    }

    public boolean consume(int gid, int num) {
        int ok = jdbc.update("UPDATE GOODS SET GNUM = GNUM - ? WHERE GID= ? AND GNUM >= ?;",
                num, gid, num);
        return ok == 1;
    }

    /**
     * 将good对象从数据库中删除
     *
     * @param good 待删除的商品对象,为null时方法返回false
     * @return 如果对象存在并且被删除返回true, 否则返回false
     */
    public boolean delete(Good good) {
        int ok = jdbc.update("DELETE FROM GOODS where GID = ?;", good.getGId());
        log.info("delete Good " + good + (ok == 1 ? " ok" : " fail"));
        return ok == 1;
    }

    /**
     * 按照商品名称查询商品是否存在
     *
     * @param GName 待查询的名称
     * @return 如果存在返回商品Good对象, 不存在时返回@code{null}
     */
    public Good getByGName(String GName) {
        if (GName == null) {
            return null;
        }
        return jdbc.queryForObject("SELECT * FROM GOODS where GNAME = ?;", new GoodMapper(), GName);
    }

    /**
     * 返回数据库中现有商品的列表
     *
     * @return 如果返回失败, 列表为空
     */
    public List<Good> getAll() {
        return jdbc.query("SELECT GID, GNAME, GPRICE, GNUM FROM GOODS;", new GoodMapper());
    }

    /**
     * 按照商品名称关键字进行模糊搜索
     *
     * @param nameSeg 通配符关键字, 形如"巧克力%", 为null时返回空list
     * @return 返回包含结果的list
     */
    public List<Good> fuzzyGet(String nameSeg) {
        List<Good> list;
        list = jdbc.query("SELECT * FROM GOODS WHERE GNAME LIKE ?;", new GoodMapper(), nameSeg);
        return list;
    }


    private static class GoodMapper implements RowMapper<Good> {
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
        public Good mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("GID");
            String name = rs.getString("GNAME");
            double price = rs.getDouble("GPRICE");
            int num = rs.getInt("GNUM");
            return new Good(id, name, price, num);
        }
    }
}

