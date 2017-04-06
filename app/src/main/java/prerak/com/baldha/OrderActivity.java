package prerak.com.baldha;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prerak.com.baldha.model.login.getProduct.GetProduct;
import prerak.com.baldha.model.login.getcategory.Category;
import prerak.com.baldha.model.login.getcategory.GetCategory;
import prerak.com.baldha.service.APIService;
import prerak.com.baldha.util.AppConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bhadresh Chavada on 06-04-2017.
 */

public class OrderActivity extends Activity {

    LinearLayout layout_ordermenu;
    Button btn_add_layout;
    View view;
    private List<String> mList = new ArrayList<>();
    private List<String> mShopList = new ArrayList<>();
    private List<String> mProductList = new ArrayList<>();
    private List<Category> mCategories = new ArrayList<>();
    private Spinner mCategory, mProduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        init();
    }

    void init() {
        layout_ordermenu = (LinearLayout) findViewById(R.id.layout_ordermenu);


//        LayoutInflater mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        view = mInflater.inflate(R.layout.order_layout, null, false);
//        layout_ordermenu.addView(view);


        btn_add_layout = (Button) findViewById(R.id.btn_add_layout);

        mList.add("Please Select Category");
        mProductList.add("Please Select Product");

        AddView();
        btn_add_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddView();

            }
        });
    }

    void AddView() {
        LayoutInflater mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.order_layout, null, false);
        btn_add_layout.setVisibility(View.GONE);
        layout_ordermenu.addView(view);

        mCategory = (Spinner) view.findViewById(R.id.sp_category);
        mProduct = (Spinner) view.findViewById(R.id.sp_product);

        EditText et_quantity = (EditText) view.findViewById(R.id.et_quantity);
        et_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btn_add_layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getCategory() {
//        AppConstant.showProgressDialog(OrderActivity.this, "Loading", "Please Wait");
        final APIService categoryService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<GetCategory> categoryCall = categoryService.getCategoryCall();
        Log.d("url", categoryCall.request().url().toString());
        categoryCall.enqueue(new Callback<GetCategory>() {
            @Override
            public void onResponse(Call<GetCategory> call, Response<GetCategory> response) {
                if (response.body() != null && response.body().getCategory() != null) {
//                    AppConstant.hideProgressDialog();

                    if (mList.size() > 0) {
                        mList.clear();
                    }
                    mList.add("Please Select Category");
                    for (int i = 0; i < response.body().getCategory().size(); i++) {
                        mList.add(response.body().getCategory().get(i).getCatName());
                    }
                    final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(OrderActivity.this, android.R.layout.simple_spinner_item, mList);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mCategory.setAdapter(categoryAdapter);

                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(OrderActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCategory> call, Throwable t) {
                AppConstant.hideProgressDialog();
                Toast.makeText(OrderActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProduct() {
//        AppConstant.showProgressDialog(OrderActivity.this, "Loading", "Please Wait");
        final APIService productService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Map<String, String> productMap = new HashMap<>();
        productMap.put("catID", "1");
        Call<GetProduct> productCall = productService.getProductCall(productMap);
        Log.d("url", productCall.request().url().toString());
        productCall.enqueue(new Callback<GetProduct>() {
            @Override
            public void onResponse(Call<GetProduct> call, Response<GetProduct> response) {
                if (response.body() != null && response.body().getProduct() != null) {
//                    AppConstant.hideProgressDialog();
                    if (mProductList.size() > 0) {
                        mProductList.clear();
                    }
                    mProductList.add("Please Select Product");
                    for (int i = 0; i < response.body().getProduct().size(); i++) {
                        mProductList.add(response.body().getProduct().get(i).getProductName());
                    }
                    final ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(OrderActivity.this, android.R.layout.simple_spinner_item, mProductList);
                    productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mProduct.setAdapter(productAdapter);
                    // getProduct(mCategories.get(position).getCatID());
                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(OrderActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProduct> call, Throwable t) {
                AppConstant.hideProgressDialog();
                Toast.makeText(OrderActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
