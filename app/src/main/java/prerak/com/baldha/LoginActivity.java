package prerak.com.baldha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import prerak.com.baldha.model.login.ForgotPass;
import prerak.com.baldha.model.login.Login;
import prerak.com.baldha.service.APIService;
import prerak.com.baldha.util.AppConstant;
import prerak.com.baldha.util.SharedPreferences_baldaha;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    //Todo: variable declaration
    private EditText mUserName, mPassword;
    private TextView mForgot_password;
    private Button mLogin;
    private APIService mAPIService;
    private ProgressDialog mProgressDialog;
    private String userName, pass, email;
    private EditText mEditText_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        init();
        //ToDo Progress Dialog Declaration
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("PLEASE WAIT");
        mProgressDialog.setCancelable(false);

        onClick();
    }

    private void onClick() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            Intent mIntent=new Intent(LoginActivity.this,MainActivity.class);
//            startActivity(mIntent);
                if (AppConstant.isNetworkAvailable(LoginActivity.this)) {
                    userName = mUserName.getText().toString();
                    pass = mPassword.getText().toString();
                    if (mUserName.getText().toString().length() == 0) {
                        mUserName.setError("Enter User Name");
                        mUserName.requestFocus();
                    } else if (mPassword.getText().toString().length() == 0) {
                        mPassword.setError("Enter Password");
                        mPassword.requestFocus();
                    } else {
                        mProgressDialog.show();
                        Map<String, String> mLoginMap = new HashMap<String, String>();
                        mLoginMap.put("username", userName);
                        mLoginMap.put("password", pass);
                        mAPIService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
                        Call<Login> mLoginCall = mAPIService.getLoginCall(mLoginMap);
                        Log.d("url", mLoginCall.request().url().toString());
                        mLoginCall.enqueue(new Callback<Login>() {
                            @Override
                            public void onResponse(Call<Login> call, Response<Login> response) {
                                mProgressDialog.hide();
                                if (response.body() != null) {
                                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                                        mProgressDialog.hide();
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(mIntent);
                                        finish();
                                    } else {
                                        mProgressDialog.hide();
                                        Toast.makeText(LoginActivity.this, "Response false", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    mProgressDialog.hide();
                                    Toast.makeText(LoginActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Login> call, Throwable t) {

                                mProgressDialog.hide();
                                Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Please Check Your Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        mForgot_password.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LayoutInflater layoutInflater_factory = LayoutInflater.from(LoginActivity.this);
//                showForgetPasswordDialog(layoutInflater_factory);
//            }
//        });
    }

    //Todo: initialization here
    private void init() {
        mUserName = (EditText) findViewById(R.id.et_userName);
        mPassword = (EditText) findViewById(R.id.et_password);
        mForgot_password = (TextView) findViewById(R.id.tv_forgot_password);
        mLogin = (Button) findViewById(R.id.btn_login);

    }

//    //Todo:// forgot dialog
//    private void showForgetPasswordDialog(final LayoutInflater layoutInflater_factory) {
//        final View forgetDialogView = layoutInflater_factory.inflate(R.layout.forget_password, null);
//        final AlertDialog forgetDialog = new AlertDialog.Builder(LoginActivity.this).create();
//        forgetDialog.setView(forgetDialogView);
//        forgetDialogView.findViewById(R.id.mButton_submit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //your business logic
//                mEditText_email = (EditText) forgetDialogView.findViewById(R.id.et_email);
//                email = mEditText_email.getText().toString();
//                if (!email.equalsIgnoreCase("")) {
//                    if (email.contains("@") && email.contains(".")) {
//                        mProgressDialog.show();
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("email", email);
//
//                        mAPIService = AppConstant.setupRetrofit(AppConstant.BASE_URL);
//                        Call<ForgotPass> mEmailValidationCall = mAPIService.getForgotPassCall(params);
//                        Log.e("url", "" + mEmailValidationCall.request().url());
//                        mEmailValidationCall.enqueue(new Callback<ForgotPass>() {
//                            @Override
//                            public void onResponse(Call<ForgotPass> call, Response<ForgotPass> response) {
//                                if (response.body() != null) {
//                                    if (response.body().getStatus().equalsIgnoreCase("success")) {
//                                        forgetDialog.dismiss();
//                                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                        //showChangePasswordDialog(layoutInflater_factory);
//                                        new SharedPreferences_baldaha().SaveValue(LoginActivity.this, "4", SharedPreferences_baldaha.USERID);
//
//                                    } else if (response.body().getStatus().equalsIgnoreCase("error")) {
//                                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                        mProgressDialog.dismiss();
//                                    }
//                                } else {
//                                    Toast.makeText(LoginActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<ForgotPass> call, Throwable t) {
//                                Toast.makeText(LoginActivity.this, "CONNECTION PROBLEM", Toast.LENGTH_SHORT).show();
//                                mProgressDialog.dismiss();
//                            }
//                        });
//                    } else {
//                        mEditText_email.setError("PLEASE ENTER VALID EMAIL ID");
//                        mEditText_email.requestFocus();
//                    }
//                } else {
//                    mEditText_email.setError("PLEASE ENTER EMAIL ID");
//                    mEditText_email.requestFocus();
//                }
//                //deleteDialog.dismiss();
//            }
//        });
//        forgetDialog.show();
//    }

}
