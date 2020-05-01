package com.hustcaid.myshoppingmanagement.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
@Data
public class Good {

    private int gId = -1;
    @NotNull
    private String gName;
    @Min(0)
    private double gPrice;
    @Min(1)
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

    @Override
    public String toString() {
        return "Good[id:" + gId + " name:" + gName + " price:" + gPrice + " num:" + gNum + "]";
    }

}
