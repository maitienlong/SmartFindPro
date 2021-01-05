package com.poly.smartfindpro.ui.login.loginFragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.login.req.LoginRequest;
import com.poly.smartfindpro.data.model.login.res.LoginResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragmentPresenter implements LoginFragmentContract.Presenter {
    private Context mContex;
    private LoginFragmentContract.ViewModel mViewmodel;
    private LoginResponse mLogin;
    private String username, password, token;
    private FragmentLoginBinding mBinding;

    public LoginFragmentPresenter(Context mContex, LoginFragmentContract.ViewModel mViewmodel, FragmentLoginBinding mBinding) {
        this.mContex = mContex;
        this.mViewmodel = mViewmodel;
        this.mBinding = mBinding;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public TextWatcher changeColorButton() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void afterTextChanged(Editable s) {
                if (mBinding.edtAccountNumber.length() != 0 && mBinding.edtPassword.length() != 0) {
                    mBinding.btnAction.setBackgroundTintList(mContex.getColorStateList(R.color.green));
                } else {
                    mBinding.btnAction.setBackgroundTintList(mContex.getColorStateList(R.color.background_button_login));
                }
            }
        };
    }

    public void sendRequestUser() {
        if (mBinding.edtAccountNumber.getText().toString().equals("")) {
            mViewmodel.showMessage("Vui lòng nhập tài khoản");
        } else if (mBinding.edtPassword.getText().toString().equals("")) {
            mViewmodel.showMessage("Vui lòng nhập Mật khẩu");
        } else if (!isNetworkConnected()) {
            mViewmodel.showMessage("Vui lòng kiểm tra kết nối mạng");
        } else {
            getLogin();
        }

    }

    @Override
    public void onClickLogin() {
        mViewmodel.onClickLogin();
    }

    private void getLogin() {
        mViewmodel.showLoading();
        LoginRequest request = new LoginRequest();
        request.setAccount(mBinding.edtAccountNumber.getText().toString().trim());
        request.setPassword(mBinding.edtPassword.getText().toString());

        MyRetrofitSmartFind.getInstanceSmartFind().getLogin(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.code() == 200 && response.body().getResponseHeader().getResCode() == 200) {
                    mViewmodel.hideLoading();

                    String token = response.body().getResponseBody().getUser().getId();
                    int level = response.body().getResponseBody().getUser().getLevel();
                    Config.PROFILE = response.body().getResponseBody().getUser();
                    Config.TOKEN_USER = token;
                    Config.LEVEL_ACCOUNT = level;
                    Log.d("getUser", String.valueOf(level));
                    mViewmodel.saveLogin(mBinding.edtAccountNumber.getText().toString().trim(), mBinding.edtPassword.getText().toString().trim(), token, level);
                } else {
                    mViewmodel.hideLoading();
                    mViewmodel.showMessage("Tài khoản hoặc mật khẩu không chính xác");
                }

            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mViewmodel.hideLoading();
                mViewmodel.showMessage("Vui lòng kiểm tra kết nối mạng");
            }
        });


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContex.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
