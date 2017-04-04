package prerak.com.baldha.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import prerak.com.baldha.service.APIService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

public class AppConstant {
    public static ProgressDialog progressDialog;
public static String BASE_URL = "http://yogeshkachhad.16mb.com/";
    //Todo: Internet connection check
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //Todo: Show progressbar dialog
    public static void showProgressDialog(Context context, String title, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static APIService setupRetrofit(String url) {
        /*OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();*/
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                //.client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        APIService apiService = retrofit.create(APIService.class);
        return apiService;
    }

    //Todo: Hide Progressbar dialog
    public static void hideProgressDialog() {
        progressDialog.dismiss();
    }

//    login	http://yogeshkachhad.16mb.com/webservices/login?username=superuser&password=super123
//    forgot password	http://localhost/adminpanel/webservices/forgot_password?email=kachhadyogesh050@gmail.com
//    category	http://yogeshkachhad.16mb.com/webservices/getCategory
//    product by catID	http://yogeshkachhad.16mb.com/webservices/getProduct?catID=1
//    country	http://yogeshkachhad.16mb.com/webservices/getCountry
//    state	http://yogeshkachhad.16mb.com/webservices/getState
//    district	http://yogeshkachhad.16mb.com/webservices/getDistrict
//    city	http://yogeshkachhad.16mb.com/webservices/getCity
//    insertshop	http://yogeshkachhad.16mb.com/webservices/insertShop?shopName=xyz&ownerName=xyz&address=qwe&cityID=1&areaID=1&distributorID=1&contactNO=9909556304&image=&lat=31.00&long=51.00
//    getshop	http://yogeshkachhad.16mb.coim/webservices/getShop?userID
//    inserOrder	 http://yogeshkachhad.16mb.com/webservices/insertOrder?userID=1&shopID=1&productID=1&cityID=1&categoryID=1&batterylvl=10&lat=31.00&long=33.56
//
//    getarea new	http://yogeshkachhad.16mb.com/webservices/getArea?userID=1
}

