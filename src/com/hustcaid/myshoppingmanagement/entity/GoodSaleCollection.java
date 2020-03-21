package com.hustcaid.myshoppingmanagement.entity;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/21   
 *
 ******************************************************************************/
public class GoodSaleCollection {
    private String name;
    private double price;
    private int numPreserve;
    private int numSaled;

    public GoodSaleCollection() {

    }

    public GoodSaleCollection(String name, double price, int numPreserve, int numSaled) {
        this.name = name;
        this.price = price;
        this.numPreserve = numPreserve;
        this.numSaled = numSaled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumPreserve() {
        return numPreserve;
    }

    public void setNumPreserve(int numPreserve) {
        this.numPreserve = numPreserve;
    }

    public int getNumSaled() {
        return numSaled;
    }

    public void setNumSaled(int numSaled) {
        this.numSaled = numSaled;
    }
}
