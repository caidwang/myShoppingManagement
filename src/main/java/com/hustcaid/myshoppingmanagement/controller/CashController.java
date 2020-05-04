package com.hustcaid.myshoppingmanagement.controller;

import com.hustcaid.myshoppingmanagement.dao.IGoodsDao;
import com.hustcaid.myshoppingmanagement.dao.ISalemanDao;
import com.hustcaid.myshoppingmanagement.entity.CartItemVO;
import com.hustcaid.myshoppingmanagement.entity.Good;
import com.hustcaid.myshoppingmanagement.entity.GoodSale;
import com.hustcaid.myshoppingmanagement.entity.Saleman;
import com.hustcaid.myshoppingmanagement.service.GoodSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/4/23   
 *
 ******************************************************************************/
@Controller
@RequestMapping("/cash")
@SessionAttributes({"cartItems", "saleman"})
public class CashController {
    @Autowired
    private ISalemanDao salemanDao;

    @Autowired
    private GoodSaleService goodSaleService;

    @Autowired
    private IGoodsDao goodsDao;

    @ModelAttribute("cartItems")
    public List<CartItemVO> getCart() {
        return new ArrayList<CartItemVO>();
    }

    @GetMapping
    public String main(@RequestParam(value = "session-id", required = false) String sessionId, @ModelAttribute("cartItems") List<CartItemVO> cartItems, Model model) {
        if (model.getAttribute("saleman") == null && sessionId == null) {
            return "forward:/signIn";
        }
        Saleman sm = salemanDao.getBySName(sessionId);
        model.addAttribute("saleman", sm);
        double total = 0;
        for (CartItemVO cartItemVO : cartItems) {
            total += cartItemVO.getSumMoney();
        }
        model.addAttribute("total", total);
        model.addAttribute("cartItems", cartItems);
        return "cash";
    }

    @GetMapping("details")
    public String detail(@RequestParam(value = "searchKey", required = false) String searchKey, Model model) {
        List<Good> goods;
        if (searchKey == null) {
            goods = goodsDao.getAll();
        } else {
            goods = goodsDao.getByFuzzyName("%" + searchKey + "%");
        }
        model.addAttribute("goods", goods);
        return "cashDetail";
    }

    @GetMapping("check")
    public String check(@SessionAttribute("cartItems") List<CartItemVO> cartItems, @SessionAttribute("saleman") Saleman saleman, Model model, SessionStatus status) {
        List<GoodSale> goodSales = cartItem2GoodSale(cartItems, saleman);
        try {
            goodSaleService.transaction(goodSales);
        } catch (RuntimeException e) {
            model.addAttribute("msg", "订单提交失败");
            return "cash";
        }
        status.setComplete();
        return "redirect:/cash?session-id=" + saleman.getSname();
    }

    @RequestMapping("addItem")
    public String addItem(@ModelAttribute("cartItems") List<CartItemVO> cartItems, @RequestParam("goodName") String goodName, @RequestParam("amount") Integer amount, Model model, HttpServletRequest request) {
        Good good = goodsDao.getByGName(goodName);
        if (good.getGNum() < amount) {
            model.addAttribute("msg", "商品数量超过库存数量, 请重新输入");
            return "forward:/cash/details";
        }
        cartItems.add(new CartItemVO(good, amount));
        return "redirect:/cash";
    }

    @RequestMapping("delete/{id}")
    public String deleteItem(@SessionAttribute("cartItems") List<CartItemVO> items, @PathVariable("id") int id, Model model) {
        if (id >= 0 && id < items.size()) {
            items.remove(id);
        }
        model.addAttribute("cartItems", items);
        return "cash";
    }

    private static List<GoodSale> cartItem2GoodSale(List<CartItemVO> cartItems, Saleman sm) {
        ArrayList<GoodSale> goodSales = new ArrayList<>();
        for (CartItemVO cartItem : cartItems) {
            Good good = new Good();
            good.setGId(cartItem.getGoodId());
            goodSales.add(new GoodSale(sm, good, LocalDate.now(), cartItem.getAmount()));
        }
        return goodSales;
    }
}
