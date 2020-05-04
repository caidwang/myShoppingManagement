package com.hustcaid.myshoppingmanagement.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/15   
 *
 ******************************************************************************/
@Data
@NoArgsConstructor
public class CartItemVO {
    private int goodId;
    private String goodName;
    private double goodPrice;
    private int amount;
    private double sumMoney;

    public CartItemVO(Good good, Integer amount) {
        goodId = good.getGId();
        goodName = good.getGName();
        goodPrice = good.getGPrice();
        this.amount = amount;
        sumMoney = goodPrice * amount;
    }
}
