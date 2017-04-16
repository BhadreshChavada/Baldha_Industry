package prerak.com.baldha.model.login.DailyReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bhadresh Chavada on 16-04-2017.
 */

public class DailyReportModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("report")
    @Expose
    List<report> reportList;

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

    public List<report> getReportList() {
        return reportList;
    }

    public void setReportList(List<report> reportList) {
        this.reportList = reportList;
    }
}
