package com.hustcaid.myshoppingmanagement.webview;

import com.hustcaid.myshoppingmanagement.util.FeeMakerConfiguration;
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
 *  @date 2020/3/14   
 *
 ******************************************************************************/
@WebServlet("/backend")
public class SalemanPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-type", "text/html; charset=utf-8");
        Configuration cfg = (Configuration) this.getServletContext().getAttribute(FeeMakerConfiguration.FEEMAKER_CONFIG_NAME);
        Template template = cfg.getTemplate("salemans.ftlh");
        try {
            template.process(null, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
