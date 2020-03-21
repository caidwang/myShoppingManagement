package com.hustcaid.myshoppingmanagement.webview;

import com.alibaba.fastjson.JSON;
import com.hustcaid.myshoppingmanagement.dao.GoodSaleDao;
import com.hustcaid.myshoppingmanagement.dao.SalemanDao;
import com.hustcaid.myshoppingmanagement.entity.CartItem;
import com.hustcaid.myshoppingmanagement.entity.GoodSale;
import com.hustcaid.myshoppingmanagement.entity.Saleman;
import com.hustcaid.myshoppingmanagement.util.FreeMarkerConfiguration;
import com.hustcaid.myshoppingmanagement.util.Util;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
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
@WebServlet("/cash")
public class CashPage extends HttpServlet {
    private static final String PARAM_SEARCH_KEY = "searchKey";

    /**
     * 如果没有登录, 返回登录界面
     * 如果已登录, 并且没有searchKey参数, 返回默认的cash页面
     * 如果带有searchKey参数, 使用参数进行模糊查询, 并返回结果
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getParameter("session-id");
        String searchKey;
        if (sessionId == null) {
            resp.sendRedirect("/signIn");
            return;
        }
        Saleman sm = SalemanDao.isExists(sessionId);

        resp.setHeader("Content-type", "text/html; charset=utf-8");
        Configuration cfg = (Configuration) this.getServletContext().getAttribute(FreeMarkerConfiguration.FREEMARKER_CONFIG_NAME);
        Template template = cfg.getTemplate("cash.ftlh");
        Map map = new HashMap();
        map.put("saleman", sm.getSName());

        try {
            template.process(map, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        String sessionId = req.getParameter("session-id");
        Saleman sm = SalemanDao.isExists(sessionId);
        if (sm == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "session-id invalid");
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader br = req.getReader();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        List<CartItem> cartItems = JSON.parseArray(sb.toString(), CartItem.class);
        List<GoodSale> goodSales = Util.convertCartItem2GoodSale(cartItems, sm, LocalDate.now());
        if (GoodSaleDao.create(goodSales)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("{\"status\":\"OK\"}");
        } else {
            resp.getWriter().println("{\"status\":\"FAIL\"}");
        }
    }
}
