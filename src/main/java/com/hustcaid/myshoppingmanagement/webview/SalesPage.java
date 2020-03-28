package com.hustcaid.myshoppingmanagement.webview;

import com.hustcaid.myshoppingmanagement.dao.GoodSaleDao;
import com.hustcaid.myshoppingmanagement.entity.GoodSaleCollection;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
public class SalesPage extends AbstractPage {
    @Autowired
    private GoodSaleDao goodSaleDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        Configuration cfg = ((FreeMarkerConfigurer) applicationContext.getBean("freemarkerConfig")).getConfiguration();
        resp.setContentType("text/html; charset=utf-8");
        Template template = cfg.getTemplate("sales.ftlh");
        List<GoodSaleCollection> goodSaleCollections = goodSaleDao.getByDate(LocalDate.now());
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("goodsaleColletions", goodSaleCollections);
            template.process(map, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
