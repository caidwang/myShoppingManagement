package com.hustcaid.myshoppingmanagement.dao;

import com.hustcaid.myshoppingmanagement.db.DbUtil;
import com.hustcaid.myshoppingmanagement.entity.GoodSale;
import com.hustcaid.myshoppingmanagement.view.GoodSalePage.GSEntry;

import java.sql.*;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class GoodSaleDao {
    public static class SaleLog {
        public String name;
        public double price;
        public int num;
        public int saled;
        public SaleLog(String name, double price, int num, int saled) {
            this.name = name;
            this.price = price;
            this.num = num;
            this.saled = saled;
        }
    }
    public static boolean create(List<GoodSale> gsList) {
        if (gsList == null || gsList.size() == 0) {
            return false;
        }
        Connection conn = DbUtil.getConnection();
        Statement stmt = null;
        PreparedStatement pstmtG = null;
        PreparedStatement pstmtGS = null;
        ResultSet rs = null;
        Iterator<GoodSale> it = gsList.iterator();
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            pstmtG = conn.prepareStatement("UPDATE GOODS SET GNUM = GNUM - ? WHERE GID= ? AND GNUM > ?;");
            pstmtGS = conn.prepareStatement("INSERT INTO GSALES (GID, SID, SDATE, SNUM) VALUES (?, ?, ?, ?);");
            while (it.hasNext()) {
                GoodSale gs = it.next();
                pstmtG.setInt(1, gs.getNumToSale());
                pstmtG.setInt(2, gs.getGID());
                pstmtG.setInt(3, gs.getNumToSale());
                pstmtGS.setInt(1, gs.getGID());
                pstmtGS.setInt(2, gs.getSID());
                pstmtGS.setDate(3, Date.valueOf(gs.getDate()));
                pstmtGS.setInt(4, gs.getNumToSale());
                pstmtG.executeUpdate();
                pstmtGS.executeUpdate();
            }
            conn.commit();
            return true;
        }
        catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ee) { ee.printStackTrace(); }
            return false;
        }
        finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DbUtil.close(null, pstmtG, rs);
            DbUtil.close(conn, pstmtGS, null);
        }
    }

    /**
     * 按照销售日期返回销售记录
     * @param date 待查询的销售日期
     * @return GoodSale的列表, 当date非法或没有销售记录时, 列表为空.
     */
    public static List<GSEntry> getByDate(LocalDate date) {
        LinkedList<GSEntry> list = new LinkedList<>();
        if (date == null) {
            return list;
        }
        PreparedStatement pstmt = null;
        Connection conn = DbUtil.getConnection();
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT GNAME, GPRICE, GNUM, TOTAL FROM SALELIST WHERE SDATE = ?;");
            pstmt.setDate(1, Date.valueOf(date));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("GNAME");
                double price = rs.getDouble("GPRICE");
                int gnum = rs.getInt("GNUM");
                int total = rs.getInt("TOTAL");
                list.add(new GSEntry(name, price, gnum, total));
            }
            return list;
        } catch (SQLException e) { e.printStackTrace(); return list;}
        finally {
            DbUtil.close(conn, pstmt, rs);
        }
    }
}
