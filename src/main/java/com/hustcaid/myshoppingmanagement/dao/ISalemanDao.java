package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.entity.Saleman;
import org.apache.ibatis.annotations.*;

import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/28   
 *
 ******************************************************************************/
public interface ISalemanDao {
    @Insert("insert into SALESMAN(SNAME, SPASSWORD) values (#{sname}, #{spassword})")
    @Options(useGeneratedKeys = true, keyProperty = "sid", keyColumn = "SID")
    int add(Saleman sm);

    @Update("UPDATE SALESMAN SET SNAME=#{sname}, SPASSWORD=#{spassword} WHERE SID=#{sid}")
    int modify(Saleman sm);

    @Delete("DELETE FROM SALESMAN where SID = #{sid}")
    int deleteById(Saleman saleman);

    @Select("SELECT * FROM SALESMAN where SNAME = #{sname}")
    @Results(id = "salemanMapper", value = {
            @Result(property = "sid", column = "SID"),
            @Result(property = "spassword", column = "SPASSWORD"),
            @Result(property = "sname", column = "SNAME")
    })
    Saleman getBySName(String sName);

    @Select("select * from SALESMAN")
    @ResultMap("salemanMapper")
    List<Saleman> getAll();

    @Select("SELECT * FROM SALESMAN WHERE SNAME LIKE #{keyword}")
    List<Saleman> getByFuzzyName(String keyWord);
}
