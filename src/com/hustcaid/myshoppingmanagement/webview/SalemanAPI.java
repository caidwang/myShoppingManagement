package com.hustcaid.myshoppingmanagement.webview;

import com.alibaba.fastjson.JSON;
import com.hustcaid.myshoppingmanagement.dao.SalemanDao;
import com.hustcaid.myshoppingmanagement.entity.Saleman;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/21   
 *
 ******************************************************************************/
@WebServlet("/saleman/*")
public class SalemanAPI extends HttpServlet {
    private static final String PARAM_PATTERN = "pattern";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String saleman = preProcess(req, resp);
        String pattern = req.getParameter(PARAM_PATTERN);
        List<Saleman> salemans;
        if ("all".equals(pattern)) {
            salemans = SalemanDao.getAll();
        } else if ("fuzzy".equals(pattern)) {
            salemans = SalemanDao.fuzzyGet(saleman + "%");
        } else {
            salemans = new ArrayList<>();
            Saleman item;
            if ((item = SalemanDao.isExists(saleman)) != null) {
                salemans.add(item);
            }
        }
        String result = JSON.toJSONString(salemans);
        resp.setHeader("Content-type", "application/json; charset=utf-8");
        resp.getWriter().print(result);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        preProcess(req, resp);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = req.getReader();
        String str;
        boolean cast;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        Saleman newSaleman = null;
        try {
            newSaleman = JSON.parseObject(sb.toString(), Saleman.class);
            cast = true;
        } catch (Exception e) {
            e.printStackTrace();
            cast = false;
        }

        if (cast && SalemanDao.add(newSaleman)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("{\"status\":\"OK\"}");
        } else {
            resp.setStatus(400);
            resp.getWriter().println("{\"status\":\"FAIL\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sName = preProcess(req, resp);
        Saleman modifiedSM = SalemanDao.isExists(sName);
        if (modifiedSM != null) {
            Enumeration<String> params = req.getParameterNames();
            boolean modified = false;
            while (params.hasMoreElements()) {
                String param = params.nextElement();
                switch (param) {
                    case "SName":
                        modified = true;
                        modifiedSM.setSName(req.getParameter(param));
                        break;
                    case "SPassword":
                        modified = true;
                        modifiedSM.setSPassword(req.getParameter(param));
                        break;
                    default:
                        ;
                }
            }
            if (modified && SalemanDao.modify(modifiedSM)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println("{\"status\":\"OK\"}");
                return;
            }
        }
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().println("{\"status\":\"FAIL\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gName = preProcess(req, resp);
        Saleman deletedSM = SalemanDao.isExists(gName);
        if (SalemanDao.delete(deletedSM)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("{\"status\":\"OK\"}");
        } else {
            resp.setStatus(400);
            resp.getWriter().println("{\"status\":\"FAIL\"}");
        }
    }

    private String preProcess(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json; charset=utf-8");
        String[] uri = req.getRequestURI().split("/");
        return uri[uri.length - 1];
    }
}
