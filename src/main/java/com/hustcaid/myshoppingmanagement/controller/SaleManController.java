package com.hustcaid.myshoppingmanagement.controller;

import com.hustcaid.myshoppingmanagement.dao.ISalemanDao;
import com.hustcaid.myshoppingmanagement.entity.SaleManValidator;
import com.hustcaid.myshoppingmanagement.entity.Saleman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
    public String mainPage(@RequestParam(name = "searchKey", required = false) String nameKey, Model model) {
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


    @RequestMapping("submitSaleMan")
    public String modifyUser(@ModelAttribute(name = "saleman") Saleman saleman, @RequestParam(value = "username", required = false) String username, HttpServletRequest request, BindingResult bindingResult, Model model) {
        // get方法进来时, model中没有saleman对象, spring负责自动创建并注入
        if ("POST".equals(request.getMethod())) {
            // 显式创建validator对表单进行验证
            new SaleManValidator().validate(saleman, bindingResult);
            // 表单内容验证通过
            if (!bindingResult.hasErrors()) {
                // 如果是创建销售员, 调用add
                try {
                    if (username == null) {
                        salemanDao.add(saleman);
                    }
                    // 否则调用modify
                    else {
                        // id通过form的hidden input传入model的属性中
                        model.addAttribute("username", username);
                        salemanDao.modify(saleman);
                    }
                } catch (DuplicateKeyException e) {
                    // 没有service层, 所以遇到异常需要在这一层处理
                    bindingResult.rejectValue("sname", "error.sname.duplication", "用户名已存在");
                    return "saleManDetails";
                }
                // 创建或修改通过
                return "redirect:/backend";
            }
        } else if ("GET".equals(request.getMethod())) {
            // 创建新销售员的情况
            if (username == null)
                model.addAttribute("saleman", saleman);
                // 修改销售员的情况
            else {
                model.addAttribute("username", username);
                Saleman sm = salemanDao.getBySName(username);
                // 这里的model绑定和freemarker中的语法一起使用, 将sid, sname, spassword传递到表中
                model.addAttribute("saleman", sm);
            }
        }
        // 如果是GET方法, 直接返回, 如果是POST方法,没有通过验证器验证
        return "saleManDetails";
    }
}
