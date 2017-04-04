
package prerak.com.baldha.model.login.getcategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("catID")
    @Expose
    private String catID;
    @SerializedName("catName")
    @Expose
    private String catName;
    @SerializedName("catDescription")
    @Expose
    private String catDescription;
    @SerializedName("dateCreated")
    @Expose
    private String dateCreated;
    @SerializedName("status")
    @Expose
    private String status;

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDescription() {
        return catDescription;
    }

    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
