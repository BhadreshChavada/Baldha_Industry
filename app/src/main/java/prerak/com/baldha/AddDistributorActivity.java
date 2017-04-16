package prerak.com.baldha;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import prerak.com.baldha.model.login.District.DistrictModel;
import prerak.com.baldha.model.login.InsertShop;
import prerak.com.baldha.model.login.city.City;
import prerak.com.baldha.model.login.city.CitySpinner;
import prerak.com.baldha.model.login.country.Country;
import prerak.com.baldha.model.login.country.CountrySpinner;
import prerak.com.baldha.model.login.state.State;
import prerak.com.baldha.model.login.state.StateSpinner;
import prerak.com.baldha.service.APIService;
import prerak.com.baldha.util.AppConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

/**
 * Created by Bhadresh Chavada on 26-03-2017.
 */

public class AddDistributorActivity extends AppCompatActivity {

    EditText edt_shopname, edt_ownername, edt_partenername, edt_address, edt_yearestalishment, edt_pincode, edt_vat, edt_area_bipl, edt_tin, edt_cst, edt_pan, edt_csa, edt_agriculturelicno, edt_drug_lic_no, edt_email, edt_contactno, edt_mobileno, edt_bankname, edt_accountno, edt_bankbranch, edt_office_sqr_ft, edt_gowdown_sqr_ft, edt_no_van, edt_no_auto, edt_no_cycle, edt_no_truck, edt_no_salesman, edt_any_vehicel, edt_no_any_vehicel, edt_no_employee, edt_no_working_bipl, edt_annual_turnover, edt_annual_turnover_other, edt_annual_turnover_total, edt_avg_stock_holding, edt_retailer_count, edt_freuence_visit, edt_wholesaler_count, edt_visit_frequency_days, edt_other_companyname, edt_type_business, edt_area, edt_gross_annual_turnover, edt_other_companyname_2, edt_type_business_2, edt_area_2, edt_gross_annual_turnover_2, edt_other_companyname_3, edt_type_business_3, edt_area_3, edt_gross_annual_turnover_3, edt_business_startdate, edt_bipl_area, edt_totalinvestment, bipl_annual_turnover, edt_bipl_avg_stock_holding, edt_bipl_stockvalue, edt_bipl_debators, edt_product_handled, edt_payment_tearms, edt_mode_payment;
    Button btn_submit;
    Spinner sp_consitutuion, sp_state, sp_district, sp_city, sp_gowdown_building, sp_office, sp_ready_stock_booking_supply;

    private ArrayList<String> countryNames = new ArrayList<String>();
    private List<Country> mCountries = new ArrayList<>();
    private ArrayList<String> stateNames = new ArrayList<String>();
    private ArrayList<String> districtNames = new ArrayList<String>();
    private ArrayList<String> CityNames = new ArrayList<String>();
    private List<State> mState = new ArrayList<>();
    private List<prerak.com.baldha.model.login.District.District> mDistrict = new ArrayList<>();
    private List<City> mCity = new ArrayList<>();
    String CityID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_distributor);

        init();
    }

    void init() {

        edt_shopname = (EditText) findViewById(R.id.edt_shopname);
        edt_ownername = (EditText) findViewById(R.id.edt_ownername);
        edt_partenername = (EditText) findViewById(R.id.edt_partenername);
        edt_address = (EditText) findViewById(R.id.edt_address);
        edt_yearestalishment = (EditText) findViewById(R.id.edt_yearestalishment);
        edt_pincode = (EditText) findViewById(R.id.edt_pincode);
        edt_vat = (EditText) findViewById(R.id.edt_vat);
        edt_tin = (EditText) findViewById(R.id.edt_tin);
        edt_cst = (EditText) findViewById(R.id.edt_cst);
        edt_pan = (EditText) findViewById(R.id.edt_pan);
        edt_csa = (EditText) findViewById(R.id.edt_csa);
        edt_agriculturelicno = (EditText) findViewById(R.id.edt_agriculturelicno);
        edt_drug_lic_no = (EditText) findViewById(R.id.edt_drug_lic_no);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_contactno = (EditText) findViewById(R.id.edt_contactno);
        edt_mobileno = (EditText) findViewById(R.id.edt_mobileno);
        edt_bankname = (EditText) findViewById(R.id.edt_bankname);
        edt_accountno = (EditText) findViewById(R.id.edt_accountno);
        edt_bankbranch = (EditText) findViewById(R.id.edt_bankbranch);
        edt_office_sqr_ft = (EditText) findViewById(R.id.edt_office_sqr_ft);
        edt_gowdown_sqr_ft = (EditText) findViewById(R.id.edt_gowdown_sqr_ft);
        edt_no_van = (EditText) findViewById(R.id.edt_no_van);
        edt_no_auto = (EditText) findViewById(R.id.edt_no_auto);
        edt_no_cycle = (EditText) findViewById(R.id.edt_no_cycle);
        edt_no_truck = (EditText) findViewById(R.id.edt_no_truck);
        edt_no_salesman = (EditText) findViewById(R.id.edt_no_salesman);
        edt_any_vehicel = (EditText) findViewById(R.id.edt_any_vehicel);
        edt_no_any_vehicel = (EditText) findViewById(R.id.edt_no_any_vehicel);
        edt_no_employee = (EditText) findViewById(R.id.edt_no_employee);
        edt_no_working_bipl = (EditText) findViewById(R.id.edt_no_working_bipl);
        edt_annual_turnover = (EditText) findViewById(R.id.edt_annual_turnover);
        edt_annual_turnover_other = (EditText) findViewById(R.id.edt_annual_turnover_other);
        edt_annual_turnover_total = (EditText) findViewById(R.id.edt_annual_turnover_total);
        edt_avg_stock_holding = (EditText) findViewById(R.id.edt_avg_stock_holding);
        edt_retailer_count = (EditText) findViewById(R.id.edt_retailer_count);
        edt_freuence_visit = (EditText) findViewById(R.id.edt_freuence_visit);
        edt_wholesaler_count = (EditText) findViewById(R.id.edt_wholesaler_count);
        edt_visit_frequency_days = (EditText) findViewById(R.id.edt_visit_frequency_days);
        edt_other_companyname = (EditText) findViewById(R.id.edt_other_companyname);
        edt_type_business = (EditText) findViewById(R.id.edt_type_business);
        edt_area = (EditText) findViewById(R.id.edt_area);
        edt_gross_annual_turnover = (EditText) findViewById(R.id.edt_gross_annual_turnover);
        edt_other_companyname_2 = (EditText) findViewById(R.id.edt_other_companyname_2);
        edt_type_business_2 = (EditText) findViewById(R.id.edt_type_business_2);
        edt_area_2 = (EditText) findViewById(R.id.edt_area_2);
        edt_gross_annual_turnover_2 = (EditText) findViewById(R.id.edt_gross_annual_turnover_2);
        edt_other_companyname_3 = (EditText) findViewById(R.id.edt_other_companyname_3);
        edt_type_business_3 = (EditText) findViewById(R.id.edt_type_business_3);
        edt_area_3 = (EditText) findViewById(R.id.edt_area_3);
        edt_gross_annual_turnover_3 = (EditText) findViewById(R.id.edt_gross_annual_turnover_3);
        edt_business_startdate = (EditText) findViewById(R.id.edt_business_startdate);
        edt_bipl_area = (EditText) findViewById(R.id.edt_bipl_area);
        edt_totalinvestment = (EditText) findViewById(R.id.edt_totalinvestment);
        bipl_annual_turnover = (EditText) findViewById(R.id.bipl_annual_turnover);
        edt_bipl_avg_stock_holding = (EditText) findViewById(R.id.edt_bipl_avg_stock_holding);
        edt_bipl_stockvalue = (EditText) findViewById(R.id.edt_bipl_stockvalue);
        edt_bipl_debators = (EditText) findViewById(R.id.edt_bipl_debators);
        edt_product_handled = (EditText) findViewById(R.id.edt_product_handled);
        edt_payment_tearms = (EditText) findViewById(R.id.edt_payment_tearms);
        edt_mode_payment = (EditText) findViewById(R.id.edt_mode_payment);
        edt_area_bipl = (EditText) findViewById(R.id.edt_area_bipl);

        sp_consitutuion = (Spinner) findViewById(R.id.sp_consitutuion);
        sp_state = (Spinner) findViewById(R.id.sp_state);
        sp_district = (Spinner) findViewById(R.id.sp_district);
        sp_city = (Spinner) findViewById(R.id.sp_city);
        sp_gowdown_building = (Spinner) findViewById(R.id.sp_gowdown_building);
        sp_office = (Spinner) findViewById(R.id.sp_office);
        sp_ready_stock_booking_supply = (Spinner) findViewById(R.id.sp_ready_stock_booking_supply);

        btn_submit = (Button) findViewById(R.id.btn_submit);


//        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position != 0) {
//                    getState(mCountries.get(position - 1).getCountryID());
//                    Toast.makeText(getContext(), "==>" + mCountries.get(position - 1).getCountryID(), Toast.LENGTH_SHORT).show();
//                    //  getState("1");
//
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        getState("1");
        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    getDistrict(mState.get(sp_state.getSelectedItemPosition() - 1).getStateID());
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
                    getCity(mDistrict.get(sp_district.getSelectedItemPosition() - 1).getDistrictID());
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

//                    CityID = mCity.get(position - 1).getStateID();
//                    btn_submit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDistributor();
            }
        });
    }

    void CreateDistributor() {

//        http://yogeshkachhad.16mb.com/webservices/insertDistributor?userID=1&name=j&cp=j&pn=4&cnstn=4&add=4&4yoe=&state=J&district=J&
//        cityJ=&pin=7&vat=7&tin=5&cst=7&pan=4&CSAname=H&Al_no=4&Dl_no=4&ci_email=H&ci_mo=78&ci_phone=78&bnkr_name=J&
//                bnkr_branch=G&bnkr_acno=55&ifr_ofc_bldg=J&ifr_ofc_area=5&ifr_godwn_bldg=J&ifr_godwn_area=8&ifr_area_bipl=5&
//                ifr_van=&4ifr_auto=4&ifr_cycle=4&ifr_truck=4&ifr_dman=4&ifr_any=&ifr_no=4&ifr_no_emp=4&ifr_no_emp_bipl=4&
//                bd_ant_bipl=8&bd_ant_other=&1bd_ant_total=9&bd_avg_stk_holding=1&bd_rd_stk_bk_sp=1&bd_rt_count=4&bd_rt_vi_freq=1&
//                bd_wl_count=4&bd_wl_vi_freq=4&cmp1_name=h&cmp1_top=4&cmp1_area=5&cmp1_gat=5&cmp2_name=j&cmp2_top=8&cmp2_area=5&
//                cmp2_gat=4&cmp3_name=h&cmp3_top=4&cmp3_area=4&cmp3_gat=8&bd_dosbw_bipl=7&bd_area=h&bd_titw_bipl=5&bd_ant=4&
//                bd_avg_stk_hld=4&bd_stk_val=4


        HashMap<String, String> param = new HashMap<>();
        param.put("userID", "4");
        param.put("name", edt_shopname.getText().toString());
        param.put("cp", edt_ownername.getText().toString());
        param.put("pn", edt_partenername.getText().toString());
        param.put("add", edt_address.getText().toString());
        param.put("yoe", edt_yearestalishment.getText().toString());
        param.put("pin", edt_pincode.getText().toString());
        param.put("vat", edt_vat.getText().toString());
        param.put("tin", edt_tin.getText().toString());
        param.put("cst", edt_cst.getText().toString());
        param.put("pan", edt_pan.getText().toString());
        param.put("CSAname", edt_csa.getText().toString());
        param.put("Al_no", edt_agriculturelicno.getText().toString());
        param.put("Dl_no", edt_drug_lic_no.getText().toString());
        param.put("ci_email", edt_email.getText().toString());
        param.put("ci_phone", edt_contactno.getText().toString());
        param.put("ci_mo", edt_mobileno.getText().toString());
        param.put("bnkr_name", edt_bankname.getText().toString());
        param.put("bnkr_acno", edt_accountno.getText().toString());
        param.put("bnkr_branch", edt_bankbranch.getText().toString());
        param.put("ifr_ofc_area", edt_office_sqr_ft.getText().toString());
        param.put("ifr_godwn_area", edt_gowdown_sqr_ft.getText().toString());
        param.put("ifr_van", edt_no_van.getText().toString());
        param.put("ifr_auto", edt_no_auto.getText().toString());
        param.put("ifr_cycle", edt_no_cycle.getText().toString());
        param.put("ifr_truck", edt_no_truck.getText().toString());
        param.put("ifr_dman", edt_no_salesman.getText().toString());
        param.put("ifr_any", edt_any_vehicel.getText().toString());
        param.put("ifr_no", edt_no_any_vehicel.getText().toString());
        param.put("ifr_no_emp", edt_no_employee.getText().toString());
        param.put("ifr_no_emp_bipl", edt_no_working_bipl.getText().toString());
        param.put("bd_ant_bipl", edt_annual_turnover.getText().toString());
        param.put("bd_ant_other", edt_annual_turnover_other.getText().toString());
        param.put("bd_ant_total", edt_annual_turnover_total.getText().toString());
        param.put("bd_avg_stk_holding", edt_avg_stock_holding.getText().toString());
        param.put("bd_rt_count", edt_retailer_count.getText().toString());
        param.put("bd_rt_vi_freq", edt_freuence_visit.getText().toString());
        param.put("bd_wl_count", edt_wholesaler_count.getText().toString());
        param.put("bd_wl_vi_freq", edt_visit_frequency_days.getText().toString());
        param.put("cmp1_name", edt_other_companyname.getText().toString());
        param.put("cmp1_top", edt_type_business.getText().toString());
        param.put("cmp1_area", edt_area.getText().toString());
        param.put("cmp1_gat", edt_gross_annual_turnover.getText().toString());
        param.put("cmp2_name", edt_other_companyname_2.getText().toString());
        param.put("cmp2_top", edt_type_business_2.getText().toString());
        param.put("cmp2_area", edt_area_2.getText().toString());
        param.put("cmp2_gat", edt_gross_annual_turnover_2.getText().toString());
        param.put("cmp3_name", edt_other_companyname_3.getText().toString());
        param.put("cmp3_top", edt_type_business_3.getText().toString());
        param.put("cmp3_area", edt_area_3.getText().toString());
        param.put("cmp3_gat", edt_gross_annual_turnover_3.getText().toString());
        param.put("bd_dosbw_bipl", edt_business_startdate.getText().toString());
        param.put("bd_area", edt_bipl_area.getText().toString());
        param.put("bd_titw_bipl", edt_totalinvestment.getText().toString());
        param.put("bd_ant", bipl_annual_turnover.getText().toString());
        param.put("bd_avg_stk_hld", edt_bipl_avg_stock_holding.getText().toString());
        param.put("bd_stk_val", edt_bipl_stockvalue.getText().toString());
        param.put("bd_dbtr_val", edt_bipl_debators.getText().toString());
        param.put("bd_pr_hdl", edt_product_handled.getText().toString());
        param.put("bd_pymt", edt_payment_tearms.getText().toString());
        param.put("bd_md_pymt", edt_mode_payment.getText().toString());
        param.put("cnstn", sp_consitutuion.getSelectedItem().toString());
        param.put("state", sp_state.getSelectedItem().toString());
        param.put("district", sp_district.getSelectedItem().toString());
        param.put("city", sp_city.getSelectedItem().toString());
        param.put("ifr_godwn_bldg", sp_gowdown_building.getSelectedItem().toString());
        param.put("ifr_ofc_bldg", sp_office.getSelectedItem().toString());
        param.put("bd_rd_stk_bk_sp", sp_ready_stock_booking_supply.getSelectedItem().toString());
        param.put("ifr_area_bipl", edt_area_bipl.getText().toString());

        APIService registerService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<InsertShop> insertDistributorCall = registerService.insertDistributorCall(param);
        Log.d("url", insertDistributorCall.request().url().toString());
        insertDistributorCall.enqueue(new Callback<InsertShop>() {
            @Override
            public void onResponse(Call<InsertShop> call, Response<InsertShop> response) {
//                AppConstant.hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("success")) {
                        //startActivity(new Intent(SignUp.this, Verified.class));
                        //finish();
                        Toast.makeText(AddDistributorActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddDistributorActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    AppConstant.hideProgressDialog();
                    Toast.makeText(AddDistributorActivity.this, "Response error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertShop> call, Throwable t) {

//                AppConstant.hideProgressDialog();
            }
        });


    }

    private void getState(final String countryID) {
//        AppConstant.showProgressDialog(AddDistributorActivity.this, "Loading", "Please Wait");
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
                    final ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(AddDistributorActivity.this, android.R.layout.simple_spinner_item, stateNames);
                    countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_state.setAdapter(countryAdapter);

                    mState = response.body().getState();

                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(AddDistributorActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
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
                    final ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(AddDistributorActivity.this, android.R.layout.simple_spinner_item, districtNames);
                    countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_district.setAdapter(countryAdapter);

                    mDistrict = response.body().getDistrict();

                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(AddDistributorActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
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
                        if (response.body().getCity().get(i).getDistrictID().equals(s))
                            CityNames.add(response.body().getCity().get(i).getCityName());
                    }
                    final ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(AddDistributorActivity.this, android.R.layout.simple_spinner_item, CityNames);
                    countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_city.setAdapter(countryAdapter);

                    mCity = response.body().getCity();

                } else {
                    AppConstant.hideProgressDialog();
                    Toast.makeText(AddDistributorActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CitySpinner> call, Throwable t) {

                AppConstant.hideProgressDialog();
            }
        });
    }
}
