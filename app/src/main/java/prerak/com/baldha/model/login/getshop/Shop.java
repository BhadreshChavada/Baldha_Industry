
package prerak.com.baldha.model.login.getshop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shop {

    @SerializedName("shopID")
    @Expose
    private String shopID;
    @SerializedName("shopName")
    @Expose
    private String shopName;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("cityID")
    @Expose
    private String cityID;
    @SerializedName("contactNO")
    @Expose
    private String contactNO;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;

    private String AreaID;
    String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAreaID() {
        return AreaID;
    }

    public void setAreaID(String areaID) {
        AreaID = areaID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getContactNO() {
        return contactNO;
    }

    public void setContactNO(String contactNO) {
        this.contactNO = contactNO;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

}
