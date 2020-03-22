package com.hustcaid.myshoppingmanagement.entity;

import java.time.LocalDate;


/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class GoodSale {
    private int GSID;
    private int GID;
    private int SID;
    private LocalDate date;
    private int numToSale;

    public GoodSale(int GSID, int GID, int SID, LocalDate date, int num) {
        this.GSID = GSID;
        this.GID = GID;
        this.SID = SID;
        this.date = date;
        this.numToSale = num;
    }

    public GoodSale(Saleman saleman, Good good, LocalDate date, int numToSale) {
        SID = saleman.getSID();
        GID = good.getGId();
        this.numToSale = numToSale;
        this.date = date;
    }

    public int getGSID() {
        return GSID;
    }

    public void setGSID(int GSID) {
        this.GSID = GSID;
    }

    public int getGID() {
        return GID;
    }

    public void setGID(int GID) {
        this.GID = GID;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumToSale() {
        return numToSale;
    }

    public void setNumToSale(int numToSale) {
        this.numToSale = numToSale;
    }
}
