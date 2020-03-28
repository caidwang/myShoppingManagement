package com.hustcaid.myshoppingmanagement.webview;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/14   
 *
 ******************************************************************************/
@WebServlet("/backend")
public class SalemanPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        Configuration cfg = ((FreeMarkerConfigurer) applicationContext.getBean("freemarkerConfig")).getConfiguration();
        resp.setHeader("Content-type", "text/html; charset=utf-8");

        Template template = cfg.getTemplate("salemans.ftlh");
        try {
            template.process(null, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
