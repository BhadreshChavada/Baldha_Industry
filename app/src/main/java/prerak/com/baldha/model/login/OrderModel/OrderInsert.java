package prerak.com.baldha.model.login.OrderModel;

/**
 * Created by AMD21 on 28/4/17.
 */

public class OrderInsert {

    String ID, SHOPID, PRODUCTID, CATEGORYID, BETTERYLEVEL, LAT, LON, QUANTITY;

    public OrderInsert(String SHOPID, String PRODUCTID, String CATEGORYID, String BETTERYLEVEL, String LAT, String LON, String QUANTITY) {
        this.SHOPID = SHOPID;
        this.PRODUCTID = PRODUCTID;
        this.CATEGORYID = CATEGORYID;
        this.BETTERYLEVEL = BETTERYLEVEL;
        this.LAT = LAT;
        this.LON = LON;
        this.QUANTITY = QUANTITY;
    }

    public OrderInsert() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSHOPID() {
        return SHOPID;
    }

    public void setSHOPID(String SHOPID) {
        this.SHOPID = SHOPID;
    }

    public String getPRODUCTID() {
        return PRODUCTID;
    }

    public void setPRODUCTID(String PRODUCTID) {
        this.PRODUCTID = PRODUCTID;
    }

    public String getCATEGORYID() {
        return CATEGORYID;
    }

    public void setCATEGORYID(String CATEGORYID) {
        this.CATEGORYID = CATEGORYID;
    }

    public String getBETTERYLEVEL() {
        return BETTERYLEVEL;
    }

    public void setBETTERYLEVEL(String BETTERYLEVEL) {
        this.BETTERYLEVEL = BETTERYLEVEL;
    }

    public String getLAT() {
        return LAT;
    }

    public void setLAT(String LAT) {
        this.LAT = LAT;
    }

    public String getLON() {
        return LON;
    }

    public void setLON(String LON) {
        this.LON = LON;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }
}
