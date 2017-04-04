
package prerak.com.baldha.model.login.District;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class District {

    @SerializedName("districtID")
    @Expose
    private String districtID;
    @SerializedName("districtName")
    @Expose
    private String districtName;
    @SerializedName("stateID")
    @Expose
    private String stateID;
    @SerializedName("countryID")
    @Expose
    private String countryID;

    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

}
