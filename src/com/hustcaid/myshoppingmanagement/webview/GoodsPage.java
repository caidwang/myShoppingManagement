package com.hustcaid.myshoppingmanagement.webview;

import com.alibaba.fastjson.JSON;
import com.hustcaid.myshoppingmanagement.dao.GoodSaleDao;
import com.hustcaid.myshoppingmanagement.dao.GoodsDao;
import com.hustcaid.myshoppingmanagement.dao.SalemanDao;
import com.hustcaid.myshoppingmanagement.entity.CartItem;
import com.hustcaid.myshoppingmanagement.entity.Good;
import com.hustcaid.myshoppingmanagement.entity.GoodSale;
import com.hustcaid.myshoppingmanagement.entity.Saleman;
import com.hustcaid.myshoppingmanagement.util.FeeMakerConfiguration;
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
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Configuration cfg = (Configuration) this.getServletContext().getAttribute(FeeMakerConfiguration.FEEMAKER_CONFIG_NAME);
        Template template = cfg.getTemplate("goods.ftlh");
        try {
            template.process(null, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
