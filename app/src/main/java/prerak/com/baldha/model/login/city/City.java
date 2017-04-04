
package prerak.com.baldha.model.login.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("cityID")
    @Expose
    private String cityID;
    @SerializedName("cityName")
    @Expose
    private String cityName;
    @SerializedName("districtID")
    @Expose
    private String districtID;
    @SerializedName("stateID")
    @Expose
    private String stateID;
    @SerializedName("countryID")
    @Expose
    private String countryID;

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
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
