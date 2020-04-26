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
    private int sid;
    @NotNull
    private String spassword;
    @NotNull
    private String sname;

    @Override
    public String toString() {
        return "SaleMan[id:" + sid + " Name:" + sname + "]";
    }
}
