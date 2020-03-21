package com.hustcaid.myshoppingmanagement.entity;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/15   
 *
 ******************************************************************************/
public class CartItem {
    private int gid;
    private int amount;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
