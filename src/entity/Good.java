package entity;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public class Good {
    private int GId = -1;
    private String GName;
    private double GPrice;
    private int GNum;
    public Good(String name, double price, int num) {
        this.GName = name;
        this.GPrice = price;
        this.GNum = num;
    }
    public Good(int id, String name, double price, int num) {
        this(name, price, num);
        this.GId = id;
    }
    public Good(int id, String name) {
        this.GId = id;
        this.GName = name;
    }
    public Good(int id, double price) {
        this.GId = id;
        this.GPrice = price;
    }
    public Good(int id, int num) {
        this.GId = id;
        this.GNum = num;
    }

    public int getGId() {
        return GId;
    }

    public void setGId(int GId) {
        this.GId = GId;
    }

    public String getGName() {
        return GName;
    }

    public void setGName(String GName) {
        this.GName = GName;
    }

    public double getGPrice() {
        return GPrice;
    }

    public void setGPrice(double GPrice) {
        this.GPrice = GPrice;
    }

    public int getGNum() {
        return GNum;
    }

    public void setGNum(int GNum) {
        this.GNum = GNum;
    }

}
