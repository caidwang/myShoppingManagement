package com.hustcaid.myshoppingmanagement.webview;

import com.hustcaid.myshoppingmanagement.dao.SalemanDao;
import com.hustcaid.myshoppingmanagement.entity.Saleman;
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
            return ;
        }
        resp.setHeader("Content-type", "text/html; charset=utf-8");
        Saleman sm = SalemanDao.isExists(sessionId);
        if ((searchKey = req.getParameter(PARAM_SEARCH_KEY)) == null) {
            Configuration cfg = (Configuration) this.getServletContext().getAttribute(FeeMakerConfiguration.FEEMAKER_CONFIG_NAME);
            Template template = cfg.getTemplate("cash.ftlh");
            try {
                template.process(null, resp.getWriter());
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
        else {
            resp.getWriter().write("not implement.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
