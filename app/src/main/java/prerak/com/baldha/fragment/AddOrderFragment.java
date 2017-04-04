package prerak.com.baldha.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prerak.com.baldha.LoginActivity;
import prerak.com.baldha.MainActivity;
import prerak.com.baldha.R;
import prerak.com.baldha.model.login.getProduct.GetProduct;
import prerak.com.baldha.model.login.getcategory.Category;
import prerak.com.baldha.model.login.getcategory.GetCategory;
import prerak.com.baldha.model.login.getshop.GetShop;
import prerak.com.baldha.service.APIService;
import prerak.com.baldha.service.GPSTracker;
import prerak.com.baldha.util.AppConstant;
import prerak.com.baldha.util.SharedPreferences_baldaha;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOrderFragment extends Fragment {

    private View mView;
    private Spinner mCategory, mProduct, mShop;
    private EditText mQuntity;
    private Button mSubmit;
    private List<String> mList = new ArrayList<>();
    private List<String> mShopList = new ArrayList<>();
    private List<String> mProductList = new ArrayList<>();
    private List<Category> mCategories = new ArrayList<>();
    private double Lat=0.0,Lon=0.0;
    int  level;
    public AddOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add_order, container, false);

        init();

        onClick();

        getCategory();
        //getProduct();

        mShopList.add("Please Select Shop");
        mList.add("Please Select Category");
        mProductList.add("Please Select Product");


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getShop();
            }
        }, 1000);

        return mView;
    }

    private void getShop() {
//        AppConstant.showProgressDialog(getContext(), "Loading", "Please Wait");
        final APIService shopService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<GetShop> categoryCall = shopService.getShopCall();
        Log.d("url", categoryCall.request().url().toString());
        categoryCall.enqueue(new Callback<GetShop>() {
            @Override
            public void onResponse(Call<GetShop> call, Response<GetShop> response) {
                if (response.body() != null && response.body().getShop() != null) {
//                    AppConstant.hideProgressDialog();
                    if (mShopList.size() > 0) {
                        mShopList.clear();
                    }
                    mShopList.add("Please Select Shop");
                    for (int i = 0; i < response.body().getShop().size(); i++) {
                        //Bundle mBundle=new Bundle();
                        //String cityID =mBundle.getString("cityID");
                        //Toast.makeText(getContext(), "cityID" + cityID, Toast.LENGTH_SHORT).show();
                        // if (cityID.equals(response.body().getShop().get(i).getCityID()))
                        mShopList.add(response.body().getShop().get(i).getShopName());
                    }
                    final ArrayAdapter<String> shopAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mShopList);
                    shopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mShop.setAdapter(shopAdapter);

                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(getContext(), "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetShop> call, Throwable t) {
                AppConstant.hideProgressDialog();
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCategory() {
//        AppConstant.showProgressDialog(getContext(), "Loading", "Please Wait");
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
                    final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mList);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mCategory.setAdapter(categoryAdapter);

                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(getContext(), "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCategory> call, Throwable t) {
                AppConstant.hideProgressDialog();
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProduct() {
//        AppConstant.showProgressDialog(getContext(), "Loading", "Please Wait");
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
                    final ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mProductList);
                    productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mProduct.setAdapter(productAdapter);
                    // getProduct(mCategories.get(position).getCatID());
                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(getContext(), "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProduct> call, Throwable t) {
                AppConstant.hideProgressDialog();
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClick() {

        mCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
//                    Toast.makeText(getContext(), mCategories.get(position).getCatID(), Toast.LENGTH_SHORT).show();

                    // getProduct(mCategories.get(position).getCatID());
                    getProduct();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Lat=new GPSTracker(getContext()).getLatitude();
                Lon=new GPSTracker(getContext()).getLongitude();

                getActivity().registerReceiver(batteryInfoReceiver,	new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                InsertOrder();

            }
        });
    }

    private void init() {
        mCategory = (Spinner) mView.findViewById(R.id.sp_category);
        mProduct = (Spinner) mView.findViewById(R.id.sp_product);
        mShop = (Spinner) mView.findViewById(R.id.sp_shopName);
        mQuntity = (EditText) mView.findViewById(R.id.et_quantity);
        mSubmit = (Button) mView.findViewById(R.id.btn_submit);

        final ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mProductList);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProduct.setAdapter(productAdapter);
    }

    void InsertOrder() {
        AppConstant.showProgressDialog(getContext(), "Loading", "Please Wait");
        final APIService productService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Map<String, String> productMap = new HashMap<>();
        productMap.put("userID", new SharedPreferences_baldaha().GetValue(getActivity(), SharedPreferences_baldaha.USERID));
        productMap.put("shopID", "1");
        productMap.put("productID", "1");
        productMap.put("cityID", "1");
        productMap.put("categoryID", "1");
        productMap.put("batterylvl", String.valueOf(level));
        productMap.put("lat", String.valueOf(Lat));
        productMap.put("long", String.valueOf(Lon));

        Call<GetProduct> productCall = productService.InsertOrderCall(productMap);
        Log.d("url", productCall.request().url().toString());
        productCall.enqueue(new Callback<GetProduct>() {
            @Override
            public void onResponse(Call<GetProduct> call, Response<GetProduct> response) {

                AppConstant.hideProgressDialog();
                Intent mIntent = new Intent(getContext(), MainActivity.class);
                startActivity(mIntent);

//                if (response.body() != null) {
//
//                } else {
//                    AppConstant.hideProgressDialog();
//                    Toast.makeText(getContext(), "Response Error", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<GetProduct> call, Throwable t) {
                AppConstant.hideProgressDialog();
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);

        }
    };


}
