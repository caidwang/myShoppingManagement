package com.hustcaid.myshoppingmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class Saleman {
    private int SID;
    @NotNull
    private String SPassword;
    @NotNull
    private String SName;

    @Override
    public String toString() {
        return "SaleMan[id:" + SID + " Name:" + SName + "]";
    }
}
