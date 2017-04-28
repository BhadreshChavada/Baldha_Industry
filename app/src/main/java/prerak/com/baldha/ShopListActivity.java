package prerak.com.baldha;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prerak.com.baldha.Adapter.ShopAdapter;
import prerak.com.baldha.Sqlite.Database;
import prerak.com.baldha.model.login.GetArea.Area;
import prerak.com.baldha.model.login.GetArea.AreaList;
import prerak.com.baldha.model.login.InsertShop;
import prerak.com.baldha.model.login.OrderModel.OrderInsert;
import prerak.com.baldha.model.login.getshop.GetShop;
import prerak.com.baldha.model.login.getshop.Shop;
import prerak.com.baldha.service.APIService;
import prerak.com.baldha.service.TrackGPS;
import prerak.com.baldha.util.AppConstant;
import prerak.com.baldha.util.SharedPreferences_baldaha;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bhadresh Chavada on 06-04-2017.
 */

public class ShopListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;
    private static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 2;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_CAMERA = 3;
    RecyclerView mRecyclerView;
    ShopAdapter mAdapter;
    Button btn_add_shop, btn_cancel;

    private static final int SELECT_PICTURE = 10;
    Button btn_gallery, btn_camera, btn_submit;
    ImageView displayImage;
    private String selectedImagePath = null;
    private int CAMERA_REQUEST = 20;
    private byte[] byteArray;
    private String str_image;
    private EditText mShopName, mOwnerName, mAddress, mContact;
    private String Lat = "0.0", Lon = "0.0";
    Dialog dialog;
    private ArrayList<String> AreaName = new ArrayList<>();
    private Spinner sp_area;
    private List<AreaList> mArea;
    String USERID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoplist);
        init();
    }

    void init() {

        USERID = SharedPreferences_baldaha.GetValue(ShopListActivity.this, SharedPreferences_baldaha.USERID);

        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        SearchView searchView = (SearchView) findViewById(R.id.search);

        btn_add_shop = (Button) findViewById(R.id.btn_add_shop);
        btn_add_shop.setOnClickListener(this);
        if (AppConstant.isNetworkAvailable(ShopListActivity.this)) {
            Database database = new Database(ShopListActivity.this);
            ArrayList<Shop> mSHOP = database.getAllNewShop();
            if (mSHOP.size() > 0) {
                for (int i = 0; i < mSHOP.size(); i++) {
                    InsertNewStore(mSHOP.get(i));
                }
            }


            getShop();
        } else
            getOfflineShop();
        search(searchView);

    }



    private void InsertNewStore(final Shop shop) {

        Map<String, Object> shopMap = new HashMap<>();
        shopMap.put("shopName", shop.getShopName());
        shopMap.put("ownerName", shop.getOwnerName());
        shopMap.put("address", shop.getAddress());
        shopMap.put("cityID", "1");
        shopMap.put("contactNO", shop.getContactNO());
        shopMap.put("image", shop.getImage());
        shopMap.put("lat", shop.getLat());
        shopMap.put("long", shop.getLong());
        shopMap.put("areaID", shop.getAreaID());
        APIService registerService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<InsertShop> insertShopCall = registerService.getInsertShopCall(shopMap);
        Log.d("url", insertShopCall.request().url().toString());
        insertShopCall.enqueue(new Callback<InsertShop>() {
            @Override
            public void onResponse(Call<InsertShop> call, Response<InsertShop> response) {

                if (response.body() != null) {
                    if (response.body().getStatus().equals("success")) {
                        //startActivity(new Intent(SignUp.this, Verified.class));
                        //finish();

                        Database db = new Database(ShopListActivity.this);
                        db.deleteNewShop(shop.getID());
                        Toast.makeText(ShopListActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        init();
                    } else {
                        Toast.makeText(ShopListActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(ShopListActivity.this, "Response error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertShop> call, Throwable t) {


            }
        });
    }

    private void getShop() {
        AppConstant.showProgressDialog(ShopListActivity.this, "Get Shop List", "Please Wait");
        final APIService shopService = AppConstant.setupRetrofit(AppConstant.BASE_URL);

        Map<String, String> shopmap = new HashMap<>();
        shopmap.put("userID", USERID);

        Call<GetShop> categoryCall = shopService.getShopCall(shopmap);
        Log.d("url", categoryCall.request().url().toString());
        categoryCall.enqueue(new Callback<GetShop>() {
            @Override
            public void onResponse(Call<GetShop> call, Response<GetShop> response) {
                AppConstant.hideProgressDialog();
                if (response.body() != null && response.body().getShop() != null) {

                    Database db = new Database(ShopListActivity.this);
                    if (db.getShopCount() == response.body().getShop().size()) {

                        ArrayList<Shop> mArrayList = new ArrayList<Shop>(response.body().getShop());
                        mAdapter = new ShopAdapter(mArrayList, ShopListActivity.this);
                        mRecyclerView.setAdapter(mAdapter);

                    } else {

                        db.deleteShop();
                        ArrayList<Shop> mArrayList = new ArrayList<Shop>(response.body().getShop());

                        for (int i = 0; i < mArrayList.size(); i++) {
                            db.InsertShop(mArrayList.get(i));
                        }
                        mAdapter = new ShopAdapter(mArrayList, ShopListActivity.this);
                        mRecyclerView.setAdapter(mAdapter);


                    }

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

    public void getOfflineShop() {

//        ArrayList<Shop> mArrayList = new ArrayList<Shop>();
        Database db = new Database(ShopListActivity.this);
//        mArrayList = ;
        mAdapter = new ShopAdapter(db.getAllShop(), ShopListActivity.this);
        mRecyclerView.setAdapter(mAdapter);

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


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_add_shop:
                AddNewShop();
                break;
            case R.id.btn_gallery:
                if (ContextCompat.checkSelfPermission(ShopListActivity.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ShopListActivity.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
                } else {
                    OpenGallery();
                }
                break;
            case R.id.btn_camera:
                if (ContextCompat.checkSelfPermission(ShopListActivity.this,
                        android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ShopListActivity.this,
                            new String[]{android.Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_ACCESS_CAMERA);
                } else {
                    OpenCamera();
                }
                break;
            case R.id.btn_submit:
                if (AppConstant.isNetworkAvailable(ShopListActivity.this)) {
                    InsertStore();
                } else {
                    InsertStoreOffline();
                }
                dialog.dismiss();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;


        }


    }

    void AddNewShop() {
        dialog = new Dialog(ShopListActivity.this);
        dialog.setTitle("Add New Shop");
        dialog.setContentView(R.layout.dialog_shop);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);


        btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_gallery = (Button) dialog.findViewById(R.id.btn_gallery);
        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        displayImage = (ImageView) dialog.findViewById(R.id.img_display);

        mShopName = (EditText) dialog.findViewById(R.id.edt_shopname);
        mOwnerName = (EditText) dialog.findViewById(R.id.edt_ownername);
        mAddress = (EditText) dialog.findViewById(R.id.edt_address);
        mContact = (EditText) dialog.findViewById(R.id.edt_contact);

        sp_area = (Spinner) dialog.findViewById(R.id.sp_area);

        if (AppConstant.isNetworkAvailable(ShopListActivity.this))
            getArea();
        else
            getOfflineArea();

        btn_camera.setOnClickListener(this);
        btn_gallery.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);


        if (ContextCompat.checkSelfPermission(ShopListActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ShopListActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(ShopListActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Location();
        }

    }

    private void getOfflineArea() {

        List<AreaList> list = new Database(ShopListActivity.this).getAllArea();
        if (AreaName.size() > 0) {
//            list.clear();
            AreaName.clear();
        }
        AreaName.add("Please Select Area");
        for (int i = 0; i < list.size(); i++) {
            AreaName.add(list.get(i).getAreaName());
        }
        mArea = list;
        final ArrayAdapter<String> AreaAdapter = new ArrayAdapter<String>(ShopListActivity.this, android.R.layout.simple_spinner_item, AreaName);
        AreaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_area.setAdapter(AreaAdapter);


    }

    private void OpenGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);

    }

    private void OpenCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                displayImage.setImageURI(selectedImageUri);
                selectedImagePath = getPath(selectedImageUri);


                Uri selectedImage = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(ShopListActivity.this.getContentResolver(), selectedImage);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byteArray = stream.toByteArray();

                    Log.d("byteArray", String.valueOf(byteArray));

                    str_image = byteArray.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                Toast.makeText(SignUpActivity.this, "" + photo, Toast.LENGTH_SHORT).show();
                displayImage.setImageBitmap(photo);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byteArray = stream.toByteArray();

                str_image = byteArray.toString();
            }
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }

    void InsertStore() {

        if (AppConstant.isNetworkAvailable(ShopListActivity.this)) {
            String shopName = mShopName.getText().toString();
            String ownerName = mOwnerName.getText().toString();
            String address = mAddress.getText().toString();
            String contactNO = mContact.getText().toString();


            if (validation()) {
                AppConstant.showProgressDialog(ShopListActivity.this, "Register Shop", "Please wait");
                Map<String, Object> shopMap = new HashMap<>();
                shopMap.put("shopName", shopName);
                shopMap.put("ownerName", ownerName);
                shopMap.put("address", address);
                shopMap.put("cityID", "1");
                shopMap.put("contactNO", contactNO);
                shopMap.put("image", str_image);
                shopMap.put("lat", Lat);
                shopMap.put("long", Lon);
                shopMap.put("areaID", mArea.get(sp_area.getSelectedItemPosition() - 1).getAreaID());
                APIService registerService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
                Call<InsertShop> insertShopCall = registerService.getInsertShopCall(shopMap);
                Log.d("url", insertShopCall.request().url().toString());
                insertShopCall.enqueue(new Callback<InsertShop>() {
                    @Override
                    public void onResponse(Call<InsertShop> call, Response<InsertShop> response) {
                        AppConstant.hideProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus().equals("success")) {
                                //startActivity(new Intent(SignUp.this, Verified.class));
                                //finish();
                                Toast.makeText(ShopListActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                init();
                            } else {
                                Toast.makeText(ShopListActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            AppConstant.hideProgressDialog();
                            Toast.makeText(ShopListActivity.this, "Response error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<InsertShop> call, Throwable t) {

                        AppConstant.hideProgressDialog();
                    }
                });
            }
        } else {
            Toast.makeText(ShopListActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    void InsertStoreOffline() {
        String shopName = mShopName.getText().toString();
        String ownerName = mOwnerName.getText().toString();
        String address = mAddress.getText().toString();
        String contactNO = mContact.getText().toString();

        Shop shop = new Shop();
        shop.setShopID("TEMP10");
        shop.setShopName(shopName);
        shop.setOwnerName(ownerName);
        shop.setAddress(address);
        shop.setCityID("1");
        shop.setContactNO(contactNO);
        shop.setImage(str_image);
        shop.setLat(Lat);
        shop.setLong(Lon);
        shop.setAreaID(mArea.get(sp_area.getSelectedItemPosition() - 1).getAreaID());


        Database db = new Database(ShopListActivity.this);
        db.InsertShop(shop);
        db.InsertNewShop(shop);

        init();

    }

    private boolean validation() {
        if (mShopName.getText().toString().length() == 0) {
            mShopName.setError("Enter Shop Name");
            mShopName.requestFocus();
            return false;
        } else if (mOwnerName.getText().toString().length() == 0) {
            mOwnerName.setError("Enter Owner Name");
            mOwnerName.requestFocus();
            return false;
        } else if (mAddress.getText().toString().length() == 0) {
            mAddress.setError("Enter Address");
            mAddress.requestFocus();
            return false;
        } else if (mContact.getText().toString().length() == 0) {
            mContact.setError("Enter Contact Number");
            mContact.requestFocus();
            return false;
        } else if (mContact.getText().toString().length() != 10) {
            mContact.setError("Enter 10 Digit Contact");
            mContact.requestFocus();
            return false;
        }
        return true;
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
            case MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // gallery-related task you need to do.

                    OpenGallery();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(ShopListActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_ACCESS_CAMERA: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // camera-related task you need to do.

                    OpenCamera();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(ShopListActivity.this, "Permission denied to open Camera", Toast.LENGTH_SHORT).show();
                }
                return;
            }


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    void Location() {
        TrackGPS gps = new TrackGPS(ShopListActivity.this);

        if (gps.canGetLocation()) {


            Lon = String.valueOf(gps.getLongitude());
            Lat = String.valueOf(gps.getLatitude());

//            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        } else {

            gps.showSettingsAlert();
        }

    }

    private void getArea() {
//        AppConstant.showProgressDialog(ShopListActivity.this, "Loading", "Please Wait");
        final APIService countryService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        HashMap<String, String> map = new HashMap<>();
        map.put("userID", USERID);
        Call<Area> AreaCall = countryService.getAreaCall(map);
        Log.d("url", AreaCall.request().url().toString());
        AreaCall.enqueue(new Callback<Area>() {
            @Override
            public void onResponse(Call<Area> call, Response<Area> response) {
                if (response.body() != null && response.body().getArea() != null) {
//                    AppConstant.hideProgressDialog();

                    if (AreaName.size() > 0)
                        AreaName.clear();
                    AreaName.add("Please Select Area");
                    Database db = new Database(ShopListActivity.this);
                    if (db.getAreaCount() == response.body().getArea().size()) {
                        for (int i = 0; i < response.body().getArea().size(); i++) {
                            AreaName.add(response.body().getArea().get(i).getAreaName());
                        }
                    } else {

//                        db.DropAreaTable();

//                        db.deleteAllArea();
                        db.deleteArea();
                        for (int i = 0; i < response.body().getArea().size(); i++) {
                            AreaName.add(response.body().getArea().get(i).getAreaName());
                            db.InsertArea(response.body().getArea().get(i));
                        }
                    }

                    final ArrayAdapter<String> AreaAdapter = new ArrayAdapter<String>(ShopListActivity.this, android.R.layout.simple_spinner_item, AreaName);
                    AreaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_area.setAdapter(AreaAdapter);
                    mArea = response.body().getArea();


                } else {
//                    AppConstant.hideProgressDialog();
                    dialog.dismiss();
                    Toast.makeText(ShopListActivity.this, "To add the Shop Contact Admin... \n To assign the Area", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Area> call, Throwable t) {
//                AppConstant.hideProgressDialog();
                dialog.dismiss();
                Toast.makeText(ShopListActivity.this, "To add the Shop Contact Admin... \n To assign the Area", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
