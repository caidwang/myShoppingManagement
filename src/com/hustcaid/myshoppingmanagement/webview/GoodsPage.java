package com.hustcaid.myshoppingmanagement.webview;

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

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/11   
 *
 ******************************************************************************/

@WebServlet("/goodMaintain")
public class GoodsPage extends HttpServlet {
    private static final String PARAM_SEARCH_KEY = "searchKey";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-type", "text/html; charset=utf-8");
        Configuration cfg = (Configuration) this.getServletContext().getAttribute(FreeMarkerConfiguration.FREEMARKER_CONFIG_NAME);
        Template template = cfg.getTemplate("goods.ftlh");
        try {
            template.process(null, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
