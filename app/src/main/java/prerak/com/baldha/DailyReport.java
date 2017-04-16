package prerak.com.baldha;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import prerak.com.baldha.Adapter.DailyReportAdapter;
import prerak.com.baldha.Adapter.ShopAdapter;
import prerak.com.baldha.model.login.DailyReport.DailyReportModel;
import prerak.com.baldha.model.login.DailyReport.report;
import prerak.com.baldha.model.login.getshop.GetShop;
import prerak.com.baldha.model.login.getshop.Shop;
import prerak.com.baldha.service.APIService;
import prerak.com.baldha.util.AppConstant;
import prerak.com.baldha.util.SharedPreferences_baldaha;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bhadresh Chavada on 16-04-2017.
 */

public class DailyReport extends AppCompatActivity {

    RecyclerView recycleview_daily_report;
    String USERID;
    DailyReportAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailyreport);
        init();
    }

    void init() {
        USERID = SharedPreferences_baldaha.GetValue(DailyReport.this, SharedPreferences_baldaha.USERID);
        recycleview_daily_report = (RecyclerView) findViewById(R.id.recycleview_daily_report);
        recycleview_daily_report.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycleview_daily_report.setLayoutManager(layoutManager);

        getDailyReport();
    }

    void getDailyReport() {
        AppConstant.showProgressDialog(DailyReport.this, "Get Shop List", "Please Wait");
        final APIService shopService = AppConstant.setupRetrofit(AppConstant.BASE_URL);

        Map<String, String> shopmap = new HashMap<>();
        shopmap.put("userID", USERID);

        Call<DailyReportModel> categoryCall = shopService.dailyReportCall(shopmap);
        Log.d("url", categoryCall.request().url().toString());
        categoryCall.enqueue(new Callback<DailyReportModel>() {
            @Override
            public void onResponse(Call<DailyReportModel> call, Response<DailyReportModel> response) {
                AppConstant.hideProgressDialog();
                if (response.body() != null && response.body().getReportList() != null) {

                    ArrayList<report> mArrayList = new ArrayList<report>(response.body().getReportList());
                    mAdapter = new DailyReportAdapter(mArrayList, DailyReport.this);
                    recycleview_daily_report.setAdapter(mAdapter);

                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(DailyReport.this, "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DailyReportModel> call, Throwable t) {
                AppConstant.hideProgressDialog();
                Toast.makeText(DailyReport.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
