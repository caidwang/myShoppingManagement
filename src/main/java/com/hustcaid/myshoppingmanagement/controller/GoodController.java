package com.hustcaid.myshoppingmanagement.controller;

import com.hustcaid.myshoppingmanagement.dao.IGoodsDao;
import com.hustcaid.myshoppingmanagement.entity.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/4/23   
 *
 ******************************************************************************/
@Controller
@RequestMapping("/good")
public class GoodController {
    @Autowired
    private IGoodsDao goodsDao;

    @GetMapping
    public String main(Model model) {
        List<Good> goods = goodsDao.getAll();
        model.addAttribute("goods", goods);
        return "goods";
    }

    @GetMapping("delete")
    public String deleteGood(@RequestParam("goodname") String gName, Model model) {
        try {
            Good good = goodsDao.getByGName(gName);
            if (good != null) {
                goodsDao.delete(good);
                model.addAttribute("msg", "删除成功");
            } else {
                model.addAttribute("msg", "商品不存在");
            }
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("msg", "商品存在售出记录, 无法删除");
            return "forward:/good";
        }
        return "forward:/good";
    }

    @RequestMapping(value = "submitGood", method = {RequestMethod.GET, RequestMethod.POST})
    public String submitGood(@Valid @ModelAttribute("good") Good good, BindingResult errors, @RequestParam(value = "goodname", required = false) String gName, Model model, HttpServletRequest request) {
        if ("GET".equals(request.getMethod())) {
            //修改商品的情况
            if (gName != null) {
                model.addAttribute("goodname", gName);
                good = goodsDao.getByGName(gName);
                // 并发修改删除的情况
                if (good == null) {
                    model.addAttribute("msg", "修改的商品不存在");
                    return "forward:/good";
                }
            }
            // 新建商品的情况和修改的情况都需要把good重新添加到model中
            model.addAttribute("good", good);
        }
        // 提交表格的情况
        else if ("POST".equals(request.getMethod())) {
            if (!errors.hasErrors()) {
                // 新建商品
                try {
                    if (gName == null) {
                        goodsDao.add(good);
                    }
                    // 修改商品
                    else {
                        goodsDao.update(good);
                    }
                    return "redirect:/good";
                } catch (DuplicateKeyException e) {
                    model.addAttribute("msg", "商品名已存在");
                    errors.rejectValue("gName", "error.good.name.duplication", "商品名已存在");
                }
            }
            // Errors验证有误 或遇到 duplicateKey 绑定模型, 重新输入
            model.addAttribute("goodname", gName);
            model.addAttribute("good", good);
        }
        return "goodDetail";
    }
}
