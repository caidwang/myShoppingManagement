package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.entity.GoodSale;
import com.hustcaid.myshoppingmanagement.entity.GoodSaleVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/28   
 *
 ******************************************************************************/

public interface IGoodSaleDao {
    /**
     * 插入一条GoodSale记录, 返回受影响条数, mysql默认返回的是匹配的条数, 需要在url中进行修改
     * SelectKey和Options的功能相似, 都是给该gs添加id值
     *
     * @param goodSale
     * @return
     */
    @Insert(value = "insert into GSALES (GID, SID, SDATE, SNUM) values (#{GID}, #{SID}, #{date}, #{numToSale})")
//    @SelectKey(keyColumn = "GSID", keyProperty = "GSID", before = false, resultType = Integer.class, statement = "select last_insert_id()")
    @Options(useGeneratedKeys = true, keyProperty = "GSID", keyColumn = "GSID")
    int addGoodSale(GoodSale goodSale);

    @Select({"SELECT GNAME, GPRICE, GNUM, TOTAL FROM SALELIST WHERE SDATE = #{date}"})
    @Results(value = {
            @Result(property = "name", column = "GNAME"),
            @Result(property = "price", column = "GPRICE"),
            @Result(property = "numPreserve", column = "GNUM"),
            @Result(property = "numSaled", column = "TOTAL")
    })
    List<GoodSaleVO> getByDate(LocalDate date);
}
