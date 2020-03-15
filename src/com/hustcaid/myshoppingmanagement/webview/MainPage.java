package com.hustcaid.myshoppingmanagement.webview;


import com.hustcaid.myshoppingmanagement.util.FeeMakerConfiguration;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.hustcaid.myshoppingmanagement.util.FeeMakerConfiguration.getTemplate;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class MainPage extends javax.servlet.http.HttpServlet {
    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        Template template = getTemplate(this, "main.ftlh");
        try {
            template.process(null, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
