package prerak.com.baldha;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prerak.com.baldha.Adapter.ShopAdapter;
import prerak.com.baldha.model.login.getshop.GetShop;
import prerak.com.baldha.model.login.getshop.Shop;
import prerak.com.baldha.service.APIService;
import prerak.com.baldha.util.AppConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bhadresh Chavada on 06-04-2017.
 */

public class ShopListActivity extends Activity {

    RecyclerView mRecyclerView;
    ShopAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoplist);
        init();
    }

    void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        SearchView searchView = (SearchView) findViewById(R.id.search);

        getShop();
        search(searchView);

    }

    private void getShop() {
        AppConstant.showProgressDialog(ShopListActivity.this, "Loading", "Please Wait");
        final APIService shopService = AppConstant.setupRetrofit(AppConstant.BASE_URL);

        Map<String, String> shopmap = new HashMap<>();
        shopmap.put("userID", "4");

        Call<GetShop> categoryCall = shopService.getShopCall(shopmap);
        Log.d("url", categoryCall.request().url().toString());
        categoryCall.enqueue(new Callback<GetShop>() {
            @Override
            public void onResponse(Call<GetShop> call, Response<GetShop> response) {
                AppConstant.hideProgressDialog();
                if (response.body() != null && response.body().getShop() != null) {

                    ArrayList<Shop> mArrayList = new ArrayList<Shop>(response.body().getShop());
                    mAdapter = new ShopAdapter(mArrayList, ShopListActivity.this);
                    mRecyclerView.setAdapter(mAdapter);

                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(ShopListActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetShop> call, Throwable t) {
                AppConstant.hideProgressDialog();
                Toast.makeText(ShopListActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

}
