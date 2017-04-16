package prerak.com.baldha.model.login.DailyReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bhadresh Chavada on 16-04-2017.
 */

public class report {

    @SerializedName("count")
    @Expose
    String count;

    @SerializedName("sdate")
    @Expose
    String sdate;

    @SerializedName("edate")
    @Expose
    String edate;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }
}
