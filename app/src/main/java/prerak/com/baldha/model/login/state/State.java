
package prerak.com.baldha.model.login.state;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {

    @SerializedName("stateID")
    @Expose
    private String stateID;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("countryID")
    @Expose
    private String countryID;

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

}
