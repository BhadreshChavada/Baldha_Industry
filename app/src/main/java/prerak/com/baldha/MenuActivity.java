package prerak.com.baldha;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prerak.com.baldha.Adapter.OrderListAdapter;
import prerak.com.baldha.model.login.EndShiftModel;
import prerak.com.baldha.model.login.OrderModel.Order;
import prerak.com.baldha.model.login.OrderModel.OrderList;
import prerak.com.baldha.model.login.OrderModel.Product;
import prerak.com.baldha.model.login.ShiftModel;
import prerak.com.baldha.service.APIService;
import prerak.com.baldha.util.AppConstant;
import prerak.com.baldha.util.SharedPreferences_baldaha;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bhadresh Chavada on 06-04-2017.
 */

public class MenuActivity extends Activity implements View.OnClickListener {
    Button btn_add_order, btn_add_distributor, btn_list_order, btn_end_shift, btn_start_shift, btn_daily_report;
    String USERID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        init();
    }

    void init() {

        USERID = SharedPreferences_baldaha.GetValue(MenuActivity.this, SharedPreferences_baldaha.USERID);

        btn_add_order = (Button) findViewById(R.id.btn_neworder);
        btn_add_distributor = (Button) findViewById(R.id.button4);
        btn_list_order = (Button) findViewById(R.id.btn_list_order);
        btn_end_shift = (Button) findViewById(R.id.btn_end_shift);
        btn_start_shift = (Button) findViewById(R.id.btn_start_shift);
        btn_daily_report = (Button) findViewById(R.id.btn_daily_report);

        btn_add_order.setOnClickListener(this);
        btn_add_distributor.setOnClickListener(this);
        btn_list_order.setOnClickListener(this);
        btn_end_shift.setOnClickListener(this);
        btn_start_shift.setOnClickListener(this);
        btn_daily_report.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_neworder) {

            if (SharedPreferences_baldaha.GetValue(MenuActivity.this, SharedPreferences_baldaha.shiftId).equals("")) {
                Toast.makeText(this, "Please start the Shift ...", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MenuActivity.this, ShopListActivity.class);
                startActivity(intent);
            }
        } else if (v.getId() == R.id.button4) {
            Intent intent = new Intent(MenuActivity.this, AddDistributorActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_list_order) {
            Intent intent = new Intent(MenuActivity.this, OrderListActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_end_shift) {

            if (SharedPreferences_baldaha.GetValue(MenuActivity.this, SharedPreferences_baldaha.shiftId).equals("")) {
                Toast.makeText(this, "Before End the Shift please start Shift", Toast.LENGTH_SHORT).show();
            } else {
                EndShift();
            }
        } else if (v.getId() == R.id.btn_start_shift) {

            if (SharedPreferences_baldaha.GetValue(MenuActivity.this, SharedPreferences_baldaha.shiftId).equals("")) {
                StartShift();
            } else {
                Toast.makeText(this, "Before Start the Shift please End Shift", Toast.LENGTH_SHORT).show();
            }
//            StartShift();

        } else if (v.getId() == R.id.btn_daily_report) {

            Intent intent = new Intent(MenuActivity.this, DailyReport.class);
            startActivity(intent);

        }
    }

    private void StartShift() {
        AppConstant.showProgressDialog(MenuActivity.this, "Shift Start..", "Please Wait");
        final APIService shopService = AppConstant.setupRetrofit(AppConstant.BASE_URL);

        Map<String, String> shopmap = new HashMap<>();
        shopmap.put("userID", USERID);

        Call<ShiftModel> orderCall = shopService.startShiftCall(shopmap);
        Log.d("url", orderCall.request().url().toString());
        orderCall.enqueue(new Callback<ShiftModel>() {
            @Override
            public void onResponse(Call<ShiftModel> call, Response<ShiftModel> response) {
                AppConstant.hideProgressDialog();
                if (response.body() != null) {
                    SharedPreferences_baldaha.SaveValue(MenuActivity.this, SharedPreferences_baldaha.shiftId, String.valueOf(response.body().getShiftID()));

                    Toast.makeText(MenuActivity.this, "Your Shift has been start.", Toast.LENGTH_SHORT).show();
                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(MenuActivity.this, "Try Again...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShiftModel> call, Throwable t) {
                AppConstant.hideProgressDialog();
                Toast.makeText(MenuActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void EndShift() {
        AppConstant.showProgressDialog(MenuActivity.this, "Shift End..", "Please Wait");
        final APIService shopService = AppConstant.setupRetrofit(AppConstant.BASE_URL);

        Map<String, String> shopmap = new HashMap<>();
        shopmap.put("shiftID", SharedPreferences_baldaha.GetValue(MenuActivity.this, SharedPreferences_baldaha.shiftId));

        Call<EndShiftModel> orderCall = shopService.endShiftCall(shopmap);
        Log.d("url", orderCall.request().url().toString());
        orderCall.enqueue(new Callback<EndShiftModel>() {
            @Override
            public void onResponse(Call<EndShiftModel> call, Response<EndShiftModel> response) {
                AppConstant.hideProgressDialog();
                if (response.body() != null) {

                    SharedPreferences_baldaha.SaveValue(MenuActivity.this, SharedPreferences_baldaha.shiftId, "");


                    Toast.makeText(MenuActivity.this, "Your Shift has been End.", Toast.LENGTH_SHORT).show();
                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(MenuActivity.this, "Try Again...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EndShiftModel> call, Throwable t) {
                AppConstant.hideProgressDialog();
                Toast.makeText(MenuActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
