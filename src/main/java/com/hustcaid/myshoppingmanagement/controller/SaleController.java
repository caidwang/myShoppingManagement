package com.hustcaid.myshoppingmanagement.controller;

import com.hustcaid.myshoppingmanagement.dao.IGoodSaleDao;
import com.hustcaid.myshoppingmanagement.entity.GoodSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/4/24   
 *
 ******************************************************************************/
@Controller
@RequestMapping("/goodsales")
public class SaleController {
    @Autowired
    private IGoodSaleDao goodSaleDao;

    @GetMapping
    public String mian(Model model) {
        List<GoodSaleVO> goodSaleVOList = goodSaleDao.getByDate(LocalDate.now());
        model.addAttribute("goodsaleColletions", goodSaleVOList);
        return "sales";
    }
}
