package prerak.com.baldha.model.login.GetArea;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import prerak.com.baldha.model.login.country.Country;

/**
 * Created by Bhadresh Chavada on 16-04-2017.
 */

public class Area {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("area")
    @Expose
    private List<AreaList> area = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AreaList> getArea() {
        return area;
    }

    public void setArea(List<AreaList> area) {
        this.area = area;
    }
}
