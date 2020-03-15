package com.hustcaid.myshoppingmanagement.entity;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public final class Saleman {
    private int SID;
    private String SPassword;
    private String SName;
    public Saleman(String SName, String SPassword) {
        this.SName = SName;
        this.SPassword = SPassword;
    }
    public Saleman(int SID, String newName, String newPasswd) {
        this.SID = SID;
        this.SName = newName;
        this.SPassword = newPasswd;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        if (SID < 0) {
            throw new IllegalArgumentException("SID must > 0.");
        }
        this.SID = SID;
    }

    public String getPasswd() {
        return SPassword;
    }

    public void setPasswd(String SPassword) {
        this.SPassword = SPassword;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }
}
