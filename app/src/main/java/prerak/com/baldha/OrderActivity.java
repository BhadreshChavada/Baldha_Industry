package prerak.com.baldha;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import prerak.com.baldha.model.login.getProduct.Product;
import prerak.com.baldha.model.login.getcategory.Category;
import prerak.com.baldha.model.login.getcategory.GetCategory;
import prerak.com.baldha.service.APIService;
import prerak.com.baldha.service.GPSTracker;
//import prerak.com.baldha.service.TrackGPS;
import prerak.com.baldha.service.TrackGPS;
import prerak.com.baldha.util.AppConstant;
import prerak.com.baldha.util.SharedPreferences_baldaha;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bhadresh Chavada on 06-04-2017.
 */

public class OrderActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;
    LinearLayout layout_ordermenu;
    Button btn_add_layout;
    View view;
    private List<String> mList = new ArrayList<>();
    private List<String> mShopList = new ArrayList<>();
    private List<String> mProductList = new ArrayList<>();
    private List<Category> mCategories = new ArrayList<>();
    private List<Product> mProductArray = new ArrayList<>();
    private Spinner mCategory, mProduct;
    String CategoryID = "", ProductID = "", Quantity = "", shopID;
    EditText et_quantity;
    Button btn_submit_order;
    String Lat = "0.0", Lon = "0.0";
    int level;
    String USERID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        init();
    }

    void init() {

        USERID = SharedPreferences_baldaha.GetValue(OrderActivity.this, SharedPreferences_baldaha.USERID);
        layout_ordermenu = (LinearLayout) findViewById(R.id.layout_ordermenu);

//        LayoutInflater mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        view = mInflater.inflate(R.layout.order_layout, null, false);
//        layout_ordermenu.addView(view);

        shopID = getIntent().getStringExtra("ShopId");

        btn_add_layout = (Button) findViewById(R.id.btn_add_layout);
        btn_submit_order = (Button) findViewById(R.id.btn_submit_order);
        btn_add_layout.setVisibility(View.GONE);

        mList.add("Please Select Category");
        mProductList.add("Please Select Product");


        btn_add_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MakeString();
                AddView();

            }
        });

        btn_submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                GPSTracker gpsTracker = new GPSTracker(OrderActivity.this);
//                Lat = String.valueOf(gpsTracker.getLatitude());
//                Lon = String.valueOf(gpsTracker.getLongitude());


//                turnGPSOff();

//                Log.d("Lat", Lat);
//                Log.d("Lon", Lon);

                MakeString();

                InsertOrder();
            }
        });

        getCategory();
//        getProduct();

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(OrderActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(OrderActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(OrderActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Location();
        }

        registerReceiver(batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }

    void AddView() {
        LayoutInflater mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.order_layout, null, false);
        btn_add_layout.setVisibility(View.GONE);
        layout_ordermenu.addView(view);

        mCategory = (Spinner) view.findViewById(R.id.sp_category);
        mProduct = (Spinner) view.findViewById(R.id.sp_product);

        et_quantity = (EditText) view.findViewById(R.id.et_quantity);
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

        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(OrderActivity.this, android.R.layout.simple_spinner_item, mList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategory.setAdapter(categoryAdapter);

        final ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(OrderActivity.this, android.R.layout.simple_spinner_item, mProductList);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProduct.setAdapter(productAdapter);

        mCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    getProduct(mCategories.get(position - 1).getCatID());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getCategory() {
        AppConstant.showProgressDialog(OrderActivity.this, "Loading", "Please Wait");
        final APIService categoryService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<GetCategory> categoryCall = categoryService.getCategoryCall();
        Log.d("url", categoryCall.request().url().toString());
        categoryCall.enqueue(new Callback<GetCategory>() {
            @Override
            public void onResponse(Call<GetCategory> call, Response<GetCategory> response) {
                if (response.body() != null && response.body().getCategory() != null) {
                    AppConstant.hideProgressDialog();

                    if (mList.size() > 0) {
                        mList.clear();
                    }

                    mCategories = response.body().getCategory();
                    mList.add("Please Select Category");
                    for (int i = 0; i < response.body().getCategory().size(); i++) {
                        mList.add(response.body().getCategory().get(i).getCatName());
                    }
                    AddView();

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

    private void getProduct(String categoryId) {
        AppConstant.showProgressDialog(OrderActivity.this, "Loading", "Please Wait");
        final APIService productService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Map<String, String> productMap = new HashMap<>();
        productMap.put("catID", categoryId);
        Call<GetProduct> productCall = productService.getProductCall(productMap);
        Log.d("url", productCall.request().url().toString());
        productCall.enqueue(new Callback<GetProduct>() {
            @Override
            public void onResponse(Call<GetProduct> call, Response<GetProduct> response) {
                if (response.body() != null && response.body().getProduct() != null) {
                    AppConstant.hideProgressDialog();
                    if (mProductList.size() > 0) {
                        mProductList.clear();
                    }
                    mProductArray = response.body().getProduct();
                    mProductList.add("Please Select Product");
                    for (int i = 0; i < response.body().getProduct().size(); i++) {
                        mProductList.add(response.body().getProduct().get(i).getProductName());
                    }

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

    public void MakeString() {
        if (CategoryID.equals("")) {
            CategoryID = mCategories.get(mCategory.getSelectedItemPosition() - 1).getCatID();
        } else {
            CategoryID += "," + mCategories.get(mCategory.getSelectedItemPosition() - 1).getCatID();
        }
        if (ProductID.equals("")) {

            ProductID = mProductArray.get(mProduct.getSelectedItemPosition() - 1).getProductID();

//                    mProduct.getSelectedItem().toString();
        } else {

            ProductID += "," + mProductArray.get(mProduct.getSelectedItemPosition() - 1).getProductID();
        }
        if (Quantity.equals("")) {
            Quantity = et_quantity.getText().toString();
        } else {

            Quantity += "," + et_quantity.getText().toString();
        }

        Log.d("CategoryID", CategoryID + "--" + ProductID + "--" + Quantity);
    }

    void Location() {
        TrackGPS gps = new TrackGPS(OrderActivity.this);

        if (gps.canGetLocation()) {


            Lon = String.valueOf(gps.getLongitude());
            Lat = String.valueOf(gps.getLatitude());

//            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        } else {

            gps.showSettingsAlert();
        }

    }

    private void turnGPSOff() {

        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    Location();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    // 3 time open Location

    void InsertOrder() {
        AppConstant.showProgressDialog(OrderActivity.this, "Loading", "Please Wait");
        final APIService productService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Map<String, String> productMap = new HashMap<>();
        productMap.put("userID", USERID);
        productMap.put("shopID", shopID);
        productMap.put("productID", ProductID);
        productMap.put("categoryID", CategoryID);
        productMap.put("batterylvl", String.valueOf(level));
        productMap.put("lat", String.valueOf(Lat));
        productMap.put("long", String.valueOf(Lon));
        productMap.put("qnt", Quantity);

        Call<GetProduct> productCall = productService.InsertOrderCall(productMap);
        Log.d("url", productCall.request().url().toString());
        productCall.enqueue(new Callback<GetProduct>() {
            @Override
            public void onResponse(Call<GetProduct> call, Response<GetProduct> response) {

                AppConstant.hideProgressDialog();
                Intent mIntent = new Intent(OrderActivity.this, MenuActivity.class);
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
                Toast.makeText(OrderActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

        }
    };
}


