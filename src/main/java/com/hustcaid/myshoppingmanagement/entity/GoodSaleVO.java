package com.hustcaid.myshoppingmanagement.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/21   
 *
 ******************************************************************************/

/**
 * GoodSale的数据传输对象, 多从数据库的视图获取数据
 */

@Data
@NoArgsConstructor
public class GoodSaleVO {
    private String name;
    private double price;
    private int numPreserve;
    private int numSaled;

    public GoodSaleVO(String name, double price, int numPreserve, int numSaled) {
        this.name = name;
        this.price = price;
        this.numPreserve = numPreserve;
        this.numSaled = numSaled;
    }


}
