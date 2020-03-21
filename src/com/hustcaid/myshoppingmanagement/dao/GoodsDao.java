package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.db.DbUtil;
import com.hustcaid.myshoppingmanagement.entity.Good;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class GoodsDao {
    private enum properties {GNAME, GPRICE, GNUM}; // GOODS的所有属性, 表字段

    /**
     * 向数据库添加商品项, 并设置商品ID. 当
     * 商品@code{good}的GName为null,价格为0, 商品数量小于0时, 认为是一个无效的@code{good}
     * 对象. 如果商品名已存在, 插入失败.
     * @param good
     * @return 当执行成功时返回true, 否则返回false
     */
    public static boolean add(Good good) {
        if (good == null || good.getGName() == null || good.getGPrice() == 0 || good.getGNum() < 0) {
            return false;
        }
        Connection conn = DbUtil.getConnection();
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rt = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement("INSERT INTO GOODS (GNAME, GPRICE, GNUM) VALUES (?,?,?);");
            pstmt.setString(1, good.getGName());
            pstmt.setDouble(2, good.getGPrice());
            pstmt.setInt(3, good.getGNum());
            result = pstmt.executeUpdate();
            if (result == 1) { // 修改good的属性GID
                stmt = conn.createStatement();
                rt = stmt.executeQuery("SELECT GID FROM GOODS WHERE GNAME = '" + good.getGName() + "';");
                while (rt.next()) {
                    good.setGId(rt.getInt("GID"));
                }
                DbUtil.close(null, stmt, rt);
            }
            return result == 1;
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("商品名已存在.");
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DbUtil.close(conn, pstmt, rt);
        }
    }

    /**
     * 修改Good的商品属性
     * @param good, 待修改的商品对象, 其中属性修改
     * @param columnName 待修改的商品属性的数据库列名
     * @return 如果修改成功返回@code{true}, 修改失败返回@code{false}
     */
    public static boolean modify(Good good, String columnName) {
        properties pp = null;
        for (properties p: properties.values()) {
            if (p.name().equals(columnName)) {
                pp = p;
            }
        }
        if (pp == null) {
            return false;
        }

        if (good == null || good.getGId() < 0) {
            return false;
        }
        if (pp == properties.GNAME && good.getGName() == null) {
            return false;
        } else if (pp == properties.GPRICE && good.getGPrice() < 0) {
            return false;
        } else if (pp == properties.GNUM && good.getGNum() < 0) {
            return false;
        }
        Connection conn = DbUtil.getConnection();
        PreparedStatement pstmtQueryId = null;
        PreparedStatement pstmtUpdateColumn = null;
        ResultSet idQuery = null;
        int result = 0;
        try {
            pstmtQueryId = conn.prepareStatement("SELECT GID FROM GOODS WHERE GID=?;");
            pstmtQueryId.setInt(1, good.getGId());
            idQuery = pstmtQueryId.executeQuery();
            if (idQuery.next()) { // GID exists.
                pstmtUpdateColumn = conn.prepareStatement(String.format("UPDATE GOODS SET %s=? WHERE GID=?;", columnName));
                switch (pp) {
                    case GNAME:
                        pstmtUpdateColumn.setString(1, good.getGName());
                        break;
                    case GPRICE:
                        pstmtUpdateColumn.setDouble(1, good.getGPrice());
                        break;
                    case GNUM:
                        pstmtUpdateColumn.setInt(1, good.getGId());
                        break;
                    default:
                }

                pstmtUpdateColumn.setInt(2, good.getGId());
                result = pstmtUpdateColumn.executeUpdate();
            }
            return result != 0;

        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("商品名已存在.");
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DbUtil.close(null, pstmtQueryId, idQuery);
            DbUtil.close(conn, pstmtUpdateColumn, null);
        }
    }

    public static boolean modify(Good good) {
        if (good == null || good.getGPrice() < 0 || good.getGNum() < 0) return false;
        Connection conn = DbUtil.getConnection();
        PreparedStatement pstmtQueryId = null;
        PreparedStatement pstmtUpdate = null;
        ResultSet idQuery = null;
        int result = 0;
        try {
            pstmtQueryId = conn.prepareStatement("SELECT GID FROM GOODS WHERE GID=?;");
            pstmtQueryId.setInt(1, good.getGId());
            idQuery = pstmtQueryId.executeQuery();
            if (idQuery.next()) { // GID exists.
                pstmtUpdate = conn.prepareStatement("UPDATE GOODS SET GNAME=?,GPRICE=?,GNUM=? WHERE GID=?;");

                pstmtUpdate.setString(1, good.getGName());
                pstmtUpdate.setDouble(2, good.getGPrice());
                pstmtUpdate.setInt(3, good.getGNum());
                pstmtUpdate.setInt(4, good.getGId());
                result = pstmtUpdate.executeUpdate();
            }
            return result != 0;

        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("商品名已存在.");
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DbUtil.close(null, pstmtQueryId, idQuery);
            DbUtil.close(conn, pstmtUpdate, null);
        }
    }

    /**
     * 将good对象从数据库中删除
     * @param good 待删除的商品对象,为null时方法返回false
     * @return 如果对象存在并且被删除返回true, 否则返回false
     */
    public static boolean delete(Good good) {
        if (good == null) {
            return false;
        }
        Connection conn = DbUtil.getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement("DELETE FROM GOODS where GID = ?;");
            pstmt.setInt(1, good.getGId());
            result = pstmt.executeUpdate();
            return result == 1;
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("当前商品已存在交易信息, 无法删除.");
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DbUtil.close(conn, pstmt, null);
        }
    }
    /**
     * 按照商品名称查询商品是否存在
     * @param GName 待查询的名称
     * @return 如果存在返回商品Good对象, 不存在时返回@code{null}
     */
    public static Good isExists(String GName) {
        if (GName == null) {
            return null;
        }
        Connection conn = DbUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Good good = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM GOODS where GNAME = ?;");
            pstmt.setString(1, GName);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("GID");
                String gName = rs.getString("GNAME");
                double gPrice = rs.getDouble("GPRICE");
                int gNum = rs.getInt("GNUM");
                good = new Good(id, gName, gPrice, gNum);
            }
            return good;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return good;
        }
        finally {
            DbUtil.close(conn, pstmt, rs);
        }
    }

    /**
     * 返回数据库中现有商品的列表
     * @return 如果返回失败, 列表为空
     */
    public static List<Good> getAll() {
        Connection conn = DbUtil.getConnection();
        List<Good> list = new LinkedList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM GOODS;");
            extractFromResultSet(list, rs);
            return list;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return list;
        }
    }

    /**
     * 按照商品名称关键字进行模糊搜索
     * @param nameSeg 通配符关键字, 形如"巧克力%", 为null时返回空list
     * @return 返回包含结果的list
     */
    public static List<Good> fuzzyGet(String nameSeg) {
        Connection conn = DbUtil.getConnection();
        List<Good> list = new LinkedList<>();
        if (nameSeg == null || nameSeg.length() == 0) {
            return list;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM GOODS WHERE GNAME LIKE ?;");
            pstmt.setString(1, nameSeg);
            rs = pstmt.executeQuery();
            extractFromResultSet(list, rs);
            return list;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return list;
        }
        finally {
            DbUtil.close(conn, pstmt, rs);
        }
    }

    /**
     * 根据IDlist中的id查询并返回商品的列表
     * @param idList
     * @return
     */
    public static List<Good> getByIDList(List<Integer> idList) {
        LinkedList<Good> list = new LinkedList<>();
        if (idList == null || idList.size() == 0) {
            return list;
        }
        Connection conn = DbUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT GNAME, GPRICE, GNUM FROM GOODS WHERE GID = ? LIMIT 1;");
            for (int id : idList) {
                pstmt.setInt(1, id);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("GNAME");
                    double price = rs.getDouble("GPRICE");
                    int num = rs.getInt("GNUM");
                    list.add(new Good(id, name, price, num));
                }
                rs.close();
            }
            return list;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return list;
        }
        finally {
            DbUtil.close(conn, pstmt, rs);
        }
    }
    /**
     * 从@code{select * from GOODS}的返回ResultSet中抽取对象添加到list中
     * @param list 返回结果的对象列表
     * @param rs @code{select * from GOODS}的返回结果
     * @throws SQLException
     */
    private static void extractFromResultSet(List<Good> list, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int id = rs.getInt("GID");
            String name = rs.getString("GNAME");
            double price = rs.getDouble("GPRICE");
            int num = rs.getInt("GNUM");
            list.add(new Good(id, name, price, num));
        }
    }
    // 商品数量减少的操作放在GoodSale中实现, 与条目增加构成事务
}

