package prerak.com.baldha.model.login.OrderModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import prerak.com.baldha.model.login.getProduct.*;

/**
 * Created by Bhadresh Chavada on 16-04-2017.
 */

public class Order {

    @SerializedName("orderID")
    @Expose
    String orderID;

    @SerializedName("userID")
    @Expose
    String userID;

    @SerializedName("product")
    @Expose
    List<prerak.com.baldha.model.login.OrderModel.Product> product;

    @SerializedName("shopName")
    @Expose
    String shopName;

    @SerializedName("catName")
    @Expose
    String catName;

    @SerializedName("orderDate")
    @Expose
    String orderDate;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
