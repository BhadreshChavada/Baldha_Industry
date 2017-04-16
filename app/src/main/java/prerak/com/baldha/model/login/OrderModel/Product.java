package prerak.com.baldha.model.login.OrderModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bhadresh Chavada on 16-04-2017.
 */

public class Product {

    @SerializedName("productID")
    @Expose
    String productID;

    @SerializedName("productName")
    @Expose
    String productName;

    @SerializedName("productDescription")
    @Expose
    String productDescription;

    @SerializedName("productPrice")
    @Expose
    String productPrice;

    @SerializedName("productQnt")
    @Expose
    String productQnt;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQnt() {
        return productQnt;
    }

    public void setProductQnt(String productQnt) {
        this.productQnt = productQnt;
    }
}
