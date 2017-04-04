package prerak.com.baldha.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import prerak.com.baldha.R;
import prerak.com.baldha.model.login.District.DistrictModel;
import prerak.com.baldha.model.login.InsertShop;
import prerak.com.baldha.model.login.city.City;
import prerak.com.baldha.model.login.city.CitySpinner;
import prerak.com.baldha.model.login.country.Country;
import prerak.com.baldha.model.login.country.CountrySpinner;
import prerak.com.baldha.model.login.distric.District;
import prerak.com.baldha.model.login.state.State;
import prerak.com.baldha.model.login.state.StateSpinner;
import prerak.com.baldha.service.APIService;
import prerak.com.baldha.service.GPSTracker;
import prerak.com.baldha.util.AppConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewShopFragment extends Fragment {

    Button btn_submit;
    private EditText mShopName, mOwnerName, mAddress, mContact;
    private View mView;
    private Spinner sp_country, sp_state, sp_district, sp_city;
    private ArrayList<String> countryNames = new ArrayList<String>();
    private List<Country> mCountries = new ArrayList<>();
    private ArrayList<String> stateNames = new ArrayList<String>();
    private ArrayList<String> districtNames = new ArrayList<String>();
    private ArrayList<String> CityNames = new ArrayList<String>();
    private boolean isDropDownTouched = false;
    private List<State> mState = new ArrayList<>();
    private List<prerak.com.baldha.model.login.District.District> mDistrict = new ArrayList<>();
    private List<City> mCity = new ArrayList<>();
    private double Long;
    private double Lat;

    String CityID;

    public AddNewShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add_new_shop, container, false);

        init();

        onClick();

        getCountry();


        // getState();
        return mView;
    }

    private void init() {
        mShopName = (EditText) mView.findViewById(R.id.et_shop_name);
        mOwnerName = (EditText) mView.findViewById(R.id.et_owner_name);
        mAddress = (EditText) mView.findViewById(R.id.et_address);
        mContact = (EditText) mView.findViewById(R.id.et_contactNo);
        sp_country = (Spinner) mView.findViewById(R.id.sp_country);
        sp_state = (Spinner) mView.findViewById(R.id.sp_state);
        sp_district = (Spinner) mView.findViewById(R.id.sp_district);
        sp_city = (Spinner) mView.findViewById(R.id.sp_city);
        btn_submit = (Button) mView.findViewById(R.id.btn_submit);
        btn_submit.setVisibility(View.GONE);
    }


    private void onClick() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InsertStore();
            }
        });

        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    getState(mCountries.get(position - 1).getCountryID());
                    Toast.makeText(getContext(), "==>" + mCountries.get(position - 1).getCountryID(), Toast.LENGTH_SHORT).show();
                    //  getState("1");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    getDistrict(String.valueOf(mState.get(position - 1).getStateID()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    getCity(String.valueOf(mDistrict.get(position - 1).getStateID()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {

                    CityID = mCity.get(position - 1).getStateID();
                    btn_submit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void getCountry() {
        AppConstant.showProgressDialog(getContext(), "Loading", "Please Wait");
        final APIService countryService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<CountrySpinner> countrySpinnerCall = countryService.getCountrySpinnerCall();
        Log.d("url", countrySpinnerCall.request().url().toString());
        countrySpinnerCall.enqueue(new Callback<CountrySpinner>() {
            @Override
            public void onResponse(Call<CountrySpinner> call, Response<CountrySpinner> response) {
                if (response.body() != null && response.body().getCountry() != null) {
                    AppConstant.hideProgressDialog();
                    countryNames.add("Please Select Country Name");
                    for (int i = 0; i < response.body().getCountry().size(); i++) {
                        countryNames.add(response.body().getCountry().get(i).getCountryName());
                    }
                    final ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, countryNames);
                    countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_country.setAdapter(countryAdapter);

                    mCountries = response.body().getCountry();


                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(getContext(), "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CountrySpinner> call, Throwable t) {
                AppConstant.hideProgressDialog();
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getState(final String countryID) {
//        AppConstant.showProgressDialog(getContext(), "Loading", "Please Wait");
        APIService stateService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<StateSpinner> stateSpinnerCall = stateService.getStateSpinnerCall();
        Log.d("url", stateSpinnerCall.request().url().toString());
        stateSpinnerCall.enqueue(new Callback<StateSpinner>() {
            @Override
            public void onResponse(Call<StateSpinner> call, Response<StateSpinner> response) {
                if (response.body() != null && response.body().getState() != null) {

                    if (stateNames.size() > 0) {
                        stateNames.clear();
                    }

                    stateNames.add("Please Select State");
                    for (int i = 0; i < response.body().getState().size(); i++) {

                        if (countryID.equals(response.body().getState().get(i).getCountryID()))
                            stateNames.add(response.body().getState().get(i).getStateName());

                    }
                    final ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, stateNames);
                    countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_state.setAdapter(countryAdapter);

                    mState = response.body().getState();

                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(getContext(), "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateSpinner> call, Throwable t) {

                AppConstant.hideProgressDialog();
            }
        });
    }

    private void getDistrict(final String s) {

        APIService stateService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<DistrictModel> stateSpinnerCall = stateService.getDistrictSpinnerCall();
        Log.d("url", stateSpinnerCall.request().url().toString());
        stateSpinnerCall.enqueue(new Callback<DistrictModel>() {
            @Override
            public void onResponse(Call<DistrictModel> call, Response<DistrictModel> response) {
                if (response.body() != null && response.body().getDistrict() != null) {

                    if (districtNames.size() > 0) {
                        districtNames.clear();
                    }

                    districtNames.add("Please Select District");
                    for (int i = 0; i < response.body().getDistrict().size(); i++) {

                        if (s.equals(response.body().getDistrict().get(i).getStateID()))
                            districtNames.add(response.body().getDistrict().get(i).getDistrictName());

                    }
                    final ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, districtNames);
                    countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_district.setAdapter(countryAdapter);

                    mDistrict = response.body().getDistrict();

                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(getContext(), "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DistrictModel> call, Throwable t) {

                AppConstant.hideProgressDialog();
            }
        });
    }

    private void getCity(final String s) {

        APIService stateService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<CitySpinner> stateSpinnerCall = stateService.getCitySpinnerCall();
        Log.d("url", stateSpinnerCall.request().url().toString());
        stateSpinnerCall.enqueue(new Callback<CitySpinner>() {
            @Override
            public void onResponse(Call<CitySpinner> call, Response<CitySpinner> response) {
                if (response.body() != null && response.body().getCity() != null) {

                    if (CityNames.size() > 0) {
                        CityNames.clear();
                    }

                    CityNames.add("Please Select Zone");
                    for (int i = 0; i < response.body().getCity().size(); i++) {
                    mCity.get(i).getCityID();
                        Bundle mBundle= new Bundle();
                        mBundle.putString("cityID",mCity.get(i).getCityID());
                        if (s.equals(response.body().getCity().get(i).getStateID()))
                            CityNames.add(response.body().getCity().get(i).getCityName());

                    }
                    final ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, CityNames);
                    countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_city.setAdapter(countryAdapter);

                    mCity = response.body().getCity();

                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(getContext(), "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CitySpinner> call, Throwable t) {

                AppConstant.hideProgressDialog();
            }
        });
    }

    void InsertStore() {

        if (AppConstant.isNetworkAvailable(getContext())) {
            String shopName = mShopName.getText().toString();
            String ownerName = mOwnerName.getText().toString();
            String address = mAddress.getText().toString();
            String contactNO = mContact.getText().toString();
            Lat=new GPSTracker(getContext()).getLatitude();
            Long=new GPSTracker(getContext()).getLongitude();
            if (validation()) {
                AppConstant.showProgressDialog(getContext(), "Register Shop", "Please wait");
                Map<String, Object> shopMap = new HashMap<>();
                shopMap.put("shopName", shopName);
                shopMap.put("ownerName", ownerName);
                shopMap.put("address", address);
                shopMap.put("cityID", CityID);
                shopMap.put("contactNO", contactNO);
                shopMap.put("image", "");
                shopMap.put("lat", Lat);
                shopMap.put("long", Long);
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
                                Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            AppConstant.hideProgressDialog();
                            Toast.makeText(getContext(), "Response error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<InsertShop> call, Throwable t) {

                        AppConstant.hideProgressDialog();
                    }
                });
            }
        } else {
            Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validation(){
        if (mShopName.getText().toString().length()==0){
            mShopName.setError("Enter Shop Name");
            mShopName.requestFocus();
            return false;
        }else if (mOwnerName.getText().toString().length()==0){
            mOwnerName.setError("Enter Owner Name");
            mOwnerName.requestFocus();
            return false;
        }else if (mAddress.getText().toString().length()==0){
            mAddress.setError("Enter Address");
            mAddress.requestFocus();
            return false;
        }else if (mContact.getText().toString().length()==0){
            mContact.setError("Enter Contact Number");
            mContact.requestFocus();
            return false;
        }else if (mContact.getText().toString().length() !=10){
            mContact.setError("Enter 10 Digit Contact");
            mContact.requestFocus();
            return false;
        }
        return true;
    }
}
