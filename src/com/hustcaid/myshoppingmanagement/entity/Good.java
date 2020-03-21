package com.hustcaid.myshoppingmanagement.entity;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class Good {
    private int gId = -1;
    private String gName;
    private double gPrice;
    private int gNum;

    public Good() {
    }

    // 创建新Good时, 没有id内容
    public Good(String name, double price, int num) {
        this.gName = name;
        this.gPrice = price;
        this.gNum = num;
    }

    // 从db获得Good
    public Good(int id, String name, double price, int num) {
        this(name, price, num);
        this.gId = id;
    }

    public int getGId() {
        return gId;
    }

    public void setGId(int gId) {
        this.gId = gId;
    }

    public String getGName() {
        return gName;
    }

    public void setGName(String gName) {
        this.gName = gName;
    }

    public double getGPrice() {
        return gPrice;
    }

    public void setGPrice(double gPrice) {
        this.gPrice = gPrice;
    }

    public int getGNum() {
        return gNum;
    }

    public void setgNum(int gNum) {
        this.gNum = gNum;
    }

}
