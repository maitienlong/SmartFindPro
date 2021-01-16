package com.poly.smartfindpro.ui.intro;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.ConfigSharedPreferences;
import com.poly.smartfindpro.data.model.home.req.HomeRequest;
import com.poly.smartfindpro.data.model.home.res.HomeResponse;
import com.poly.smartfindpro.data.model.home.res.Product;
import com.poly.smartfindpro.data.model.login.req.LoginRequest;
import com.poly.smartfindpro.data.model.login.res.LoginResponse;
import com.poly.smartfindpro.data.model.product.req.ProductRequest;
import com.poly.smartfindpro.data.model.product.res.ProductResponse;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IntroPresenter implements IntroContract.Presenter {
    private Context mContex;
    private IntroContract.ViewModel mViewModel;

    public IntroPresenter(Context mContex, IntroContract.ViewModel mViewModel) {
        this.mContex = mContex;
        this.mViewModel = mViewModel;
        initData();
    }

    private void initData() {

    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public void requestData(String idPost) {
        ProductRequest request = new ProductRequest();
        request.setId(Config.TOKEN_USER);

        MyRetrofitSmartFind.getInstanceSmartFind().getAllProduct(request).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getResponseBody() != null) {
                        Products product = new Products();

                        for (Products item : response.body().getResponseBody().getProducts()) {
                            if (item.getId().equals(idPost)) {
                                product = item;
                                break;
                            }
                        }

                        if (product != null) {
                            mViewModel.onNextDetail(new Gson().toJson(product));
                        }
                    } else {
                        mViewModel.onShowDialogMsg("Không thể kết nối tới Server");
                    }

                } else {
                    mViewModel.onShowDialogMsg("Không thể kết nối tới Server");
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void getUpdateUser(String username, String password, boolean isLogin) {
        if (isNetworkConnected()) {

            LoginRequest request = new LoginRequest();

            request.setAccount(username);

            request.setPassword(password);

            request.setDeviceId(Config.TOKEN_DEVICE);


            Log.d("CheckLogin", new Gson().toJson(request));
            if (username != null && !username.isEmpty()) {


                MyRetrofitSmartFind.getInstanceSmartFind().getLogin(request).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        if (response.code() == 200) {
                            if (response.body().getResponseHeader().getResCode() == 200) {

                                Config.PROFILE = response.body().getResponseBody().getUser();

                                Config.TOKEN_USER = response.body().getResponseBody().getUser().getId();

                                Config.LEVEL_ACCOUNT = response.body().getResponseBody().getUser().getLevel();

                                if (isLogin) {
                                    mViewModel.onNextHome();
                                } else {
                                    mViewModel.onNextLogin();
                                }

                            } else {
                                mViewModel.onAccountNotAvail("Tài khoản không đúng hoặc không còn tồn tại");
                            }

                        } else {
                            mViewModel.onShowDialogMsg("Không thể kết nối tới Server");
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                        mViewModel.onShowDialogMsg("Không thể kết nối tới Server");
                    }
                });

            } else {
                mViewModel.onAccountNotAvail("Chào mừng bạn đến với SmartFind, Vui lòng chọn chức năng để tiếp tục");
            }
        } else {
            mViewModel.onShowDialogMsg("Vui lòng kiểm tra lại kết nối mạng");
        }

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContex.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
