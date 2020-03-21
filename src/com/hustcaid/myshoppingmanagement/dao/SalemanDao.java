package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.db.DbUtil;
import com.hustcaid.myshoppingmanagement.entity.Good;
import com.hustcaid.myshoppingmanagement.entity.Saleman;

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
@SuppressWarnings("ALL")
public class SalemanDao {
    private enum property {SNAME, SPASSWORD}; // saleman表表属性
    /**
     * 向数据库添加saleman记录
     * @param sm 待添加Saleman对象, 要求sName和sPassword属性不为空
     * @return 如果操作成功, 返回true,并设置sm对象的ID, 否则返回false.
     */
    public static boolean add(Saleman sm) {
        if (sm == null || sm.getSName() == null || sm.getSPassword() == null) {
            return false;
        }
        Connection conn = DbUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement("INSERT INTO SALESMAN (SNAME, SPASSWORD) values (?, ?);");
            pstmt.setString(1, sm.getSName());
            pstmt.setString(2, sm.getSPassword());
            result = pstmt.executeUpdate();
            DbUtil.close(null, pstmt, null);
            if (result == 1) {
                // 添加limit 使得数据库不需要遍历
                pstmt = conn.prepareStatement("SELECT SID FROM SALESMAN WHERE SNAME = ? LIMIT 1;");
                pstmt.setString(1, sm.getSName());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    sm.setSID(rs.getInt(1));
                }
            }
            return result==1;
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("售货员名称已存在.");
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return result == 1;
        }
        finally {
            DbUtil.close(conn, pstmt, rs);
        }
    }

    /**
     * 按照给定的saleman对象的属性值修改数据库中该对象的@code{columnName}属性
     * @param saleman 已经修改好的saleman对象
     * @param columnName 被修改的 列名
     * @return 如果操作成功返回true, 否则返回false
     */
    public static boolean modify(Saleman saleman, String columnName) {
        property pp = null;
        for (property p : property.values()) {
            if (p.name().equals(columnName)) {
                pp = p;
            }
        }
        if (pp == null) {
            return false;
        }
        if ((pp == property.SNAME && saleman.getSName() == null)
                || pp == property.SPASSWORD && saleman.getSPassword() == null) {
            return false;
        }
        String newVal = pp == property.SNAME ? saleman.getSName() : saleman.getSPassword();
        Connection conn = DbUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try {
            // 添加limit 使得数据库不需要遍历
            pstmt = conn.prepareStatement("SELECT SID FROM SALESMAN WHERE SID = ? LIMIT 1;");
            pstmt.setInt(1, saleman.getSID());
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                return false;
            }
            DbUtil.close(null, pstmt, rs);
            pstmt = conn.prepareStatement(String.format("UPDATE SALESMAN SET %s = ? WHERE SID=?;", columnName));
            pstmt.setString(1, newVal);
            pstmt.setInt(2, saleman.getSID());
            result = pstmt.executeUpdate();
            return result == 1;
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("售货员名称已存在.");
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return result == 1;
        }
        finally {
            DbUtil.close(conn, pstmt, rs);
        }
    }

    public static boolean modify(Saleman sm) {
        if (sm == null ||sm.getSName() == null || sm.getSPassword() == null) return false;
        Connection conn = DbUtil.getConnection();
        PreparedStatement pstmtQueryId = null;
        PreparedStatement pstmtUpdate = null;
        ResultSet idQuery = null;
        int result = 0;
        try {
            pstmtQueryId = conn.prepareStatement("SELECT SID FROM SALESMAN WHERE SID=?;");
            pstmtQueryId.setInt(1, sm.getSID());
            idQuery = pstmtQueryId.executeQuery();
            if (idQuery.next()) { // GID exists.
                pstmtUpdate = conn.prepareStatement("UPDATE SALESMAN SET SNAME=?, SPASSWORD=? WHERE SID=?;");

                pstmtUpdate.setString(1, sm.getSName());
                pstmtUpdate.setString(2, sm.getSPassword());
                pstmtUpdate.setInt(3, sm.getSID());
                result = pstmtUpdate.executeUpdate();
            }
            return result != 0;

        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("销售员名已存在.");
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
     * 按照id将给定的saleman对象在数据库中的记录删除
     * @param saleman 给定的saleman对象
     * @return 如果操作成功返回true, 否则返回false
     */
    public static boolean delete(Saleman saleman) {
        if (saleman == null) {
            return false;
        }
        Connection conn = DbUtil.getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement("DELETE FROM SALESMAN where SID = ?;");
            pstmt.setInt(1, saleman.getSID());
            result = pstmt.executeUpdate();
            return result == 1;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DbUtil.close(conn, pstmt, null);
        }
    }

    public static Saleman isExists(String sName) {
        if (sName == null) {
            return null;
        }
        Connection conn = DbUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Saleman sm = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM SALESMAN where SNAME = ? LIMIT 1;");
            pstmt.setString(1, sName);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("SID");
                String name = rs.getString("SNAME");
                String passwd = rs.getString("SPASSWORD");
                sm = new Saleman(id, name, passwd);
            }
            return sm;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return sm;
        }
        finally {
            DbUtil.close(conn, pstmt, rs);
        }
    }

    public static List<Saleman> getAll() {
        Connection conn = DbUtil.getConnection();
        List<Saleman> list = new LinkedList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM SALESMAN;");
            extractFromResultSet(list, rs);
            return list;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return list;
        }
    }

    /**
     * 按照模糊搜索查找销售员
     * @param keyWord 模糊搜索键值, 形如"张%"
     * @return
     */
    public static List<Saleman> fuzzyGet(String keyWord) {
        Connection conn = DbUtil.getConnection();
        List<Saleman> list = new LinkedList<>();
        if (keyWord == null || keyWord.length() == 0) {
            return list;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM SALESMAN WHERE SNAME LIKE ?;");
            pstmt.setString(1, keyWord);
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

    private static void extractFromResultSet(List<Saleman> list, ResultSet rs) throws SQLException {
         while (rs.next()) {
            int id = rs.getInt("SID");
            String name = rs.getString("SNAME");
            String passwd = rs.getString("SPASSWORD");
            list.add(new Saleman(id, name, passwd));
        }
    }

}
