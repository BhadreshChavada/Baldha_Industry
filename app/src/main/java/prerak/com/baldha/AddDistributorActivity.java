package prerak.com.baldha;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;

/**
 * Created by Bhadresh Chavada on 26-03-2017.
 */

public class AddDistributorActivity extends AppCompatActivity {

    EditText edt_shopname, edt_ownername, edt_partenername, edt_address, edt_yearestalishment, edt_pincode, edt_vat, edt_tin, edt_cst, edt_pan, edt_csa, edt_agriculturelicno, edt_drug_lic_no, edt_email, edt_contactno, edt_mobileno, edt_bankname, edt_accountno, edt_bankbranch, edt_office_sqr_ft, edt_gowdown_sqr_ft, edt_no_van, edt_no_auto, edt_no_cycle, edt_no_truck, edt_no_salesman, edt_any_vehicel, edt_no_any_vehicel, edt_no_employee, edt_no_working_bipl, edt_annual_turnover, edt_annual_turnover_other, edt_annual_turnover_total, edt_avg_stock_holding, edt_retailer_count, edt_freuence_visit, edt_wholesaler_count, edt_visit_frequency_days, edt_other_companyname, edt_type_business, edt_area, edt_gross_annual_turnover, edt_other_companyname_2, edt_type_business_2, edt_area_2, edt_gross_annual_turnover_2, edt_other_companyname_3, edt_type_business_3, edt_area_3, edt_gross_annual_turnover_3, edt_business_startdate, edt_bipl_area, edt_totalinvestment, bipl_annual_turnover, edt_bipl_avg_stock_holding, edt_bipl_stockvalue, edt_bipl_debators, edt_product_handled, edt_payment_tearms, edt_mode_payment;
    Button btn_submit;
    Spinner sp_consitutuion, sp_state, sp_district, sp_city, sp_gowdown_building, sp_office, sp_ready_stock_booking_supply;


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

        sp_consitutuion = (Spinner) findViewById(R.id.sp_consitutuion);
        sp_state = (Spinner) findViewById(R.id.sp_state);
        sp_district = (Spinner) findViewById(R.id.sp_district);
        sp_city = (Spinner) findViewById(R.id.sp_city);
        sp_gowdown_building = (Spinner) findViewById(R.id.sp_gowdown_building);
        sp_office = (Spinner) findViewById(R.id.sp_office);
        sp_ready_stock_booking_supply = (Spinner) findViewById(R.id.sp_ready_stock_booking_supply);

        btn_submit = (Button) findViewById(R.id.btn_submit);

    }

    void CreateDistributor() {

        HashMap<String, String> param = new HashMap<>();

        param.put("", edt_shopname.getText().toString());
        param.put("", edt_ownername.getText().toString());
        param.put("", edt_partenername.getText().toString());
        param.put("", edt_address.getText().toString());
        param.put("", edt_yearestalishment.getText().toString());
        param.put("", edt_pincode.getText().toString());
        param.put("", edt_vat.getText().toString());
        param.put("", edt_tin.getText().toString());
        param.put("", edt_cst.getText().toString());
        param.put("", edt_pan.getText().toString());
        param.put("", edt_csa.getText().toString());
        param.put("", edt_agriculturelicno.getText().toString());
        param.put("", edt_drug_lic_no.getText().toString());
        param.put("", edt_email.getText().toString());
        param.put("", edt_contactno.getText().toString());
        param.put("", edt_mobileno.getText().toString());
        param.put("", edt_bankname.getText().toString());
        param.put("", edt_accountno.getText().toString());
        param.put("", edt_bankbranch.getText().toString());
        param.put("", edt_office_sqr_ft.getText().toString());
        param.put("", edt_gowdown_sqr_ft.getText().toString());
        param.put("", edt_no_van.getText().toString());
        param.put("", edt_no_auto.getText().toString());
        param.put("", edt_no_cycle.getText().toString());
        param.put("", edt_no_truck.getText().toString());
        param.put("", edt_no_salesman.getText().toString());
        param.put("", edt_any_vehicel.getText().toString());
        param.put("", edt_no_any_vehicel.getText().toString());
        param.put("", edt_no_employee.getText().toString());
        param.put("", edt_no_working_bipl.getText().toString());
        param.put("", edt_annual_turnover.getText().toString());
        param.put("", edt_annual_turnover_other.getText().toString());
        param.put("", edt_annual_turnover_total.getText().toString());
        param.put("", edt_avg_stock_holding.getText().toString());
        param.put("", edt_retailer_count.getText().toString());
        param.put("", edt_freuence_visit.getText().toString());
        param.put("", edt_wholesaler_count.getText().toString());
        param.put("", edt_visit_frequency_days.getText().toString());
        param.put("", edt_other_companyname.getText().toString());
        param.put("", edt_type_business.getText().toString());
        param.put("", edt_area.getText().toString());
        param.put("", edt_gross_annual_turnover.getText().toString());
        param.put("", edt_other_companyname_2.getText().toString());
        param.put("", edt_type_business_2.getText().toString());
        param.put("", edt_area_2.getText().toString());
        param.put("", edt_gross_annual_turnover_2.getText().toString());
        param.put("", edt_other_companyname_3.getText().toString());
        param.put("", edt_type_business_3.getText().toString());
        param.put("", edt_area_3.getText().toString());
        param.put("", edt_gross_annual_turnover_3.getText().toString());
        param.put("", edt_business_startdate.getText().toString());
        param.put("", edt_bipl_area.getText().toString());
        param.put("", edt_totalinvestment.getText().toString());
        param.put("", bipl_annual_turnover.getText().toString());
        param.put("", edt_bipl_avg_stock_holding.getText().toString());
        param.put("", edt_bipl_stockvalue.getText().toString());
        param.put("", edt_bipl_debators.getText().toString());
        param.put("", edt_product_handled.getText().toString());
        param.put("", edt_payment_tearms.getText().toString());
        param.put("", edt_mode_payment.getText().toString());


    }
}
