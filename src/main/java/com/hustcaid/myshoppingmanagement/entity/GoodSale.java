package com.hustcaid.myshoppingmanagement.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;


/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
@Data
public class GoodSale implements Serializable {
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
        SID = saleman.getSid();
        GID = good.getGId();
        this.numToSale = numToSale;
        this.date = date;
    }

    @Override
    public String toString() {
        return "GoodSale{" +
                "GSID=" + GSID +
                ", GID=" + GID +
                ", SID=" + SID +
                ", date=" + date +
                ", numToSale=" + numToSale +
                '}';
    }
}
