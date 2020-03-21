package com.hustcaid.myshoppingmanagement.webview;

import com.hustcaid.myshoppingmanagement.dao.GoodSaleDao;
import com.hustcaid.myshoppingmanagement.entity.GoodSaleCollection;
import com.hustcaid.myshoppingmanagement.util.FreeMarkerConfiguration;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/21   
 *
 ******************************************************************************/
@WebServlet("/goodsales")
public class SalesPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        Configuration cfg = (Configuration) this.getServletContext().getAttribute(FreeMarkerConfiguration.FREEMARKER_CONFIG_NAME);
        Template template = cfg.getTemplate("sales.ftlh");
        List<GoodSaleCollection> goodSaleCollections = GoodSaleDao.getByDate(LocalDate.now());
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("goodsaleColletions", goodSaleCollections);
            template.process(map, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
