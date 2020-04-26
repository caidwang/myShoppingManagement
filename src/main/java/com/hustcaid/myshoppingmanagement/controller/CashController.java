package com.hustcaid.myshoppingmanagement.controller;

import com.hustcaid.myshoppingmanagement.dao.ISalemanDao;
import com.hustcaid.myshoppingmanagement.entity.Saleman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/4/23   
 *
 ******************************************************************************/
@Controller
@RequestMapping("/cash")
public class CashController {
    @Autowired
    private ISalemanDao salemanDao;

    @GetMapping
    public String main(@RequestParam(value = "session-id", required = false) String sessionId, Model model) {
        if (sessionId == null) {
            return "forward:/signIn.flh";
        }
        Saleman sm = salemanDao.getBySName(sessionId);
        model.addAttribute("saleman", sm);
        return "cash";
    }
}
