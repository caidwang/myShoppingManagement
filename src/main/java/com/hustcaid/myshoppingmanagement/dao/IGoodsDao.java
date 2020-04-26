package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.entity.Good;
import org.apache.ibatis.annotations.*;

import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/28   
 *
 ******************************************************************************/
public interface IGoodsDao {
    @Insert("INSERT INTO GOODS (GNAME, GPRICE, GNUM) VALUES (#{gName}, #{gPrice}, #{gNum})")
    @Options(useGeneratedKeys = true, keyColumn = "GID", keyProperty = "gId")
    int add(Good good);

    @Update("UPDATE GOODS SET GNAME=#{gName},GPRICE=#{gPrice},GNUM=#{gNum} WHERE GID= #{gId}")
    int update(Good good);

    @Update("UPDATE GOODS SET GNUM = GNUM - #{num} WHERE GID= #{gid} AND GNUM >= #{num}")
    int consume(@Param("gid") int gid, @Param("num") int num);

    @Delete("DELETE FROM GOODS where GID = #{gId}")
    int delete(Good good);

    @Select("SELECT * FROM GOODS where GNAME = #{gName}")
    Good getByGName(String GName);

    @Select("select * from GOODS")
    List<Good> getAll();

    @Select("SELECT * FROM GOODS where GNAME like #{nameSeg}")
    List<Good> getByFuzzyName(String nameSeg);
}
