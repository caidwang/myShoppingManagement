package com.hustcaid.myshoppingmanagement.webview;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.hustcaid.myshoppingmanagement.dao.IGoodsDao;
import com.hustcaid.myshoppingmanagement.entity.Good;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet("/goods/*")
public class GoodAPI extends AbstractPage {

    public static final String PARAM_PATTERN = "pattern";
    @Autowired
    private IGoodsDao goodsDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gName = preProcess(req, resp);
        String pattern = req.getParameter(PARAM_PATTERN);
        List<Good> goods;
        if ("all".equals(pattern)) {
            goods = goodsDao.getAll();
        } else if ("fuzzy".equals(pattern)) {
            goods = goodsDao.fuzzyGet(gName + "%");
        } else {
            goods = new ArrayList<>();
            Good item;
            if ((item = goodsDao.getByGName(gName)) != null) {
                goods.add(item);
            }
        }
        String result = JSON.toJSONString(goods);
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
        Good newGood = null;
        try {
            newGood = JSON.parseObject(sb.toString(), Good.class);
            cast = true;
        } catch (JSONException e) {
            e.printStackTrace();
            cast = false;
        }

        if (cast && goodsDao.add(newGood)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("{\"status\":\"OK\"}");
        } else {
            resp.setStatus(400);
            resp.getWriter().println("{\"status\":\"FAIL\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gName = preProcess(req, resp);
        Good modifiedGood = goodsDao.getByGName(gName);
        if (modifiedGood != null) {
            Enumeration<String> params = req.getParameterNames();
            boolean modified = false;
            while (params.hasMoreElements()) {
                String param = params.nextElement();
                switch (param) {
                    case "gName":
                        modified = true;
                        modifiedGood.setGName(req.getParameter(param));
                        break;
                    case "gPrice":
                        modified = true;
                        modifiedGood.setGPrice(Double.parseDouble(req.getParameter(param)));
                        break;
                    case "gNum":
                        modified = true;
                        modifiedGood.setGNum(Integer.parseInt(req.getParameter(param)));
                        break;
                    default:
                }
            }
            if (modified && goodsDao.modify(modifiedGood)) {
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
        Good deletedGood = goodsDao.getByGName(gName);
        if (goodsDao.delete(deletedGood)) {
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
