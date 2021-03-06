package com.hustcaid.myshoppingmanagement.webview;

import com.hustcaid.myshoppingmanagement.dao.ISalemanDao;
import com.hustcaid.myshoppingmanagement.entity.Saleman;
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
import java.util.HashMap;

/******************************************************************************
 *
 * @author caid wang
 *******************************************************************************/
@WebServlet("/signIn")
public class SignInPage extends AbstractPage {
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";
    private static final String PARAM_SESSION_ID = "session-id";

    @Autowired
    private ISalemanDao salemanDao;

    /**
     * 如果未登录, 返回登录页面, 否则使用已登录的session-id跳转到/cash页面
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sessionId;
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        resp.setContentType("text/html; charset=utf-8");
        if ((sessionId = req.getParameter(PARAM_SESSION_ID)) == null) {
            Configuration cfg = (Configuration) applicationContext.getBean("freeMarkerConfig");
            Template template = cfg.getTemplate("signIn.ftlh");
            try {
                template.process(new HashMap<String, Boolean>(2) {{
                    put("wrongPassword", false);
                }}, resp.getWriter());
                return;
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect("/cash?session-id=" + sessionId);
    }

    /**
     * 处理登录页面的表单post请求, 收集表单中的用户名和密码数据, 如果用户名和密码验证正确, 用用户名作为session-id跳转到cash页面,
     * 否则
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        Configuration cfg = ((FreeMarkerConfigurer) applicationContext.getBean("freemarkerConfig")).getConfiguration();
        String name = req.getParameter(PARAM_USERNAME);
        String password = req.getParameter(PARAM_PASSWORD);
        Saleman sm;
        if ((sm = salemanDao.getBySName(name)) != null && sm.getSPassword().equals(password)) {
            resp.sendRedirect("/cash?session-id=" + sm.getSName());
            return;
        }
        resp.setContentType("text/html; charset=utf-8");
        HashMap<String, Boolean> dataModel = new HashMap<>(2);
        dataModel.put("wrongPassword", true);
        Template template = cfg.getTemplate("signIn.ftlh");
        try {
            template.process(dataModel, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }


}
