package com.poly.smartfindpro.ui.login.loginFragment;

import android.content.Context;

import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.home.req.HomeRequest;
import com.poly.smartfindpro.data.model.home.res.HomeResponse;
import com.poly.smartfindpro.data.model.login.req.LoginRequest;
import com.poly.smartfindpro.data.model.login.res.LoginResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragmentPresenter implements LoginFragmentContract.Presenter {
    private Context mContex;
    private LoginFragmentContract.ViewModel mViewmodel;

    private String username, password, token;

    public LoginFragmentPresenter(Context mContex, LoginFragmentContract.ViewModel mViewmodel) {
        this.mContex = mContex;
        this.mViewmodel = mViewmodel;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public void sendRequestUser() {
        // ok

        String token = "5fb2073ff69b03b8f8875059";
        Config.TOKEN_USER = token;
        mViewmodel.saveLogin(username, password, token);
    }
    private void getLogin() {
        LoginRequest request = new LoginRequest();
        request.setUserName("Nguyen Thanh Nam");
        request.setPassword("271020Nam");

        MyRetrofitSmartFind.getInstanceSmartFind().getLogin(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 200) {
//                    mViewmodel.(response.body().getResponseBody().getProducts());

                } else {

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
//        MyRetrofitSmartFind.getInstanceSmartFind().

    }
}
