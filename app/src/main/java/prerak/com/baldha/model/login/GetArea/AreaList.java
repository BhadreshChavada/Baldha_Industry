package prerak.com.baldha.model.login.GetArea;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bhadresh Chavada on 16-04-2017.
 */

public class AreaList {

    @SerializedName("ID")
    @Expose
    private String AreaID;
    @SerializedName("Name")
    @Expose
    private String AreaName;

    public String getAreaID() {
        return AreaID;
    }

    public void setAreaID(String areaID) {
        AreaID = areaID;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }
}
