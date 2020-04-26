package com.hustcaid.myshoppingmanagement.controller;

import com.hustcaid.myshoppingmanagement.dao.ISalemanDao;
import com.hustcaid.myshoppingmanagement.entity.Saleman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/4/23   
 *
 ******************************************************************************/
@Controller
@RequestMapping("/backend")
public class SaleManController {
    @Autowired
    private ISalemanDao salemanDao;

    @GetMapping
    public String main(@RequestParam(name = "searchKey", required = false) String nameKey, Model model) {
        if (nameKey == null) {
            List<Saleman> salemen = salemanDao.getAll();
            model.addAttribute("salemen", salemen);
        } else {
            nameKey = nameKey + "%";
            List<Saleman> salemen = salemanDao.getByFuzzyName(nameKey);
            model.addAttribute("salemen", salemen);
        }
        return "salemans";
    }

    @GetMapping("delete")
    public String deleteUser(String username, Model model) {
        Saleman sm = salemanDao.getBySName(username);
        if (sm != null) {
            try {
                salemanDao.deleteById(sm);
            } catch (DataIntegrityViolationException e) {
                model.addAttribute("msg", "销售员已有销售记录, 无法删除");
                return "forward:/backend";
            }
            model.addAttribute("msg", "删除成功.");
        }
        return "forward:/backend";
    }

    @PostMapping("add")
    public @ResponseBody
    String addUser(@RequestBody Saleman saleman, HttpServletResponse response) {
        try {
            if (salemanDao.add(saleman) == 1) {
                return "{\"status\":\"ok\"}";
            } else {
                response.setStatus(500);
                return "{\"status\":\"fail\"}";
            }
        } catch (DuplicateKeyException e) {
            response.setStatus(400);
            return "{\"status\":\"fail\"}";
        }
    }
}
