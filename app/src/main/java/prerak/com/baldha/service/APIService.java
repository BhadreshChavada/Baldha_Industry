package prerak.com.baldha.service;

import java.util.Map;
import java.util.Objects;

import prerak.com.baldha.model.login.DailyReport.DailyReportModel;
import prerak.com.baldha.model.login.District.DistrictModel;
import prerak.com.baldha.model.login.EndShiftModel;
import prerak.com.baldha.model.login.ForgotPass;
import prerak.com.baldha.model.login.GetArea.Area;
import prerak.com.baldha.model.login.InsertShop;
import prerak.com.baldha.model.login.Login;
import prerak.com.baldha.model.login.OrderModel.OrderList;
import prerak.com.baldha.model.login.ShiftModel;
import prerak.com.baldha.model.login.city.CitySpinner;
import prerak.com.baldha.model.login.country.CountrySpinner;
import prerak.com.baldha.model.login.distric.District;
import prerak.com.baldha.model.login.distric.DistrictSpinner;
import prerak.com.baldha.model.login.getProduct.GetProduct;
import prerak.com.baldha.model.login.getcategory.GetCategory;
import prerak.com.baldha.model.login.getshop.GetShop;
import prerak.com.baldha.model.login.state.StateSpinner;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Prerak on 07-03-2017.
 */

public interface APIService {
    @GET("webservices/login")
    Call<Login> getLoginCall(@QueryMap Map<String, String> params);

    @GET("webservices/forgot_password")
    Call<ForgotPass> getForgotPassCall(
            @QueryMap Map<String, String> params
    );

    @GET("webservices/insertShop")
    Call<InsertShop> getInsertShopCall(
            @QueryMap Map<String, Object> params
    );

    @GET("webservices/insertDistributor")
    Call<InsertShop> insertDistributorCall(
            @QueryMap Map<String, String> params
    );

    @GET("webservices/getArea")
    Call<Area> getAreaCall(@QueryMap Map<String, String> params);


    @GET("webservices/getCountry")
    Call<CountrySpinner> getCountrySpinnerCall();

    @GET("webservices/getState")
    Call<StateSpinner> getStateSpinnerCall();

    @GET("webservices/getDistrict")
    Call<DistrictModel> getDistrictSpinnerCall();

    @GET("webservices/getCity")
    Call<CitySpinner> getCitySpinnerCall();

    @GET("webservices/getShop")
    Call<GetShop> getShopCall(@QueryMap Map<String, String> params);

    @GET("webservices/getOrder")
    Call<OrderList> getOrderCall(@QueryMap Map<String, String> params);

    @GET("webservices/getCategory")
    Call<GetCategory> getCategoryCall();

    @GET("webservices/getProduct")
    Call<GetProduct> getProductCall(
            @QueryMap Map<String, String> params
    );

    @GET("webservices/insertOrder")
    Call<GetProduct> InsertOrderCall(
            @QueryMap Map<String, String> params
    );

    @GET("webservices/insertUsershift")
    Call<ShiftModel> startShiftCall(@QueryMap Map<String, String> params);

    @GET("webservices/updateUsershift")
    Call<EndShiftModel> endShiftCall(@QueryMap Map<String, String> params);

    @GET("webservices/dailyReport")
    Call<DailyReportModel> dailyReportCall(@QueryMap Map<String, String> params);
}
