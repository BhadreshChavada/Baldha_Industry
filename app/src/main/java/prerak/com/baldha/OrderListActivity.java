package prerak.com.baldha;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prerak.com.baldha.Adapter.OrderListAdapter;
import prerak.com.baldha.Adapter.ShopAdapter;
import prerak.com.baldha.model.login.OrderModel.Order;
import prerak.com.baldha.model.login.OrderModel.OrderList;
import prerak.com.baldha.model.login.OrderModel.Product;
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

public class OrderListActivity extends AppCompatActivity {

    ExpandableListView order_expandable_list;
    private OrderListAdapter mAdapter;
    String USERID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);

        init();
    }

    void init() {
        USERID = SharedPreferences_baldaha.GetValue(OrderListActivity.this, SharedPreferences_baldaha.USERID);
        order_expandable_list = (ExpandableListView) findViewById(R.id.order_expandable_list);

        GetOrderList();
    }

    private void GetOrderList() {
        AppConstant.showProgressDialog(OrderListActivity.this, "Loading", "Please Wait");
        final APIService shopService = AppConstant.setupRetrofit(AppConstant.BASE_URL);

        Map<String, String> shopmap = new HashMap<>();
        shopmap.put("userID", USERID);

        Call<OrderList> orderCall = shopService.getOrderCall(shopmap);
        Log.d("url", orderCall.request().url().toString());
        orderCall.enqueue(new Callback<OrderList>() {
            @Override
            public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                AppConstant.hideProgressDialog();
                if (response.body() != null && response.body().getOrders() != null) {

                    List<Order> mArrayList = new ArrayList<>(response.body().getOrders());

                    HashMap<String, List<Product>> productmap = new HashMap<String, List<Product>>();
                    for (int i = 0; i < mArrayList.size(); i++) {
                        productmap.put(mArrayList.get(i).getOrderID(), mArrayList.get(i).getProduct());
                    }

                    mAdapter = new OrderListAdapter(mArrayList, productmap, OrderListActivity.this);
                    order_expandable_list.setAdapter(mAdapter);

                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(OrderListActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderList> call, Throwable t) {
                AppConstant.hideProgressDialog();
                Toast.makeText(OrderListActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
