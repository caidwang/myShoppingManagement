package com.hustcaid.myshoppingmanagement.controller;

import com.hustcaid.myshoppingmanagement.dao.ISalemanDao;
import com.hustcaid.myshoppingmanagement.entity.Saleman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/4/24   
 *
 ******************************************************************************/
@Controller
@RequestMapping("/signIn")
public class SignInController {
    @Autowired
    private ISalemanDao salemanDao;

    @GetMapping
    public String signPage(@RequestParam(value = "session-id", required = false) String sessionId) {
        if (sessionId != null) {
            return "forward:/cash";
        }
        return "signIn";
    }

    @PostMapping
    public String processForm(Saleman saleman, Model model) {
        String name = saleman.getSname();
        String password = saleman.getSpassword();
        Saleman other;
        if ((other = salemanDao.getBySName(name)) != null && other.getSpassword().equals(password)) {
            model.addAttribute("session-id", name);
            return "redirect: /cash";
        } else {
            model.addAttribute("msg", "用户名与密码不符, 请重新登录");
            return "signIn";
        }
    }
}
