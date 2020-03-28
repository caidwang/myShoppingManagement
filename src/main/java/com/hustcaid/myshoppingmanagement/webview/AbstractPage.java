package com.hustcaid.myshoppingmanagement.webview;

import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/28   
 *
 ******************************************************************************/
public class AbstractPage extends HttpServlet {
    /**
     * 通过重写servlet的init方法实现在servlet中注入bean
     *
     * @throws ServletException if an exception occurs that
     *                          interrupts the servlet's
     *                          normal operation
     */
    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
    }
}
