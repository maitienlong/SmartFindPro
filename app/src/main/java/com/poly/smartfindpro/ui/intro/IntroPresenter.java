package com.poly.smartfindpro.ui.intro;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.ConfigSharedPreferences;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;

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
        getUpdateUser();
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public void getUpdateUser() {

        SharedPreferences prefs = mContex.getSharedPreferences(Config.NAME_FILE_PREFERENCE, Context.MODE_PRIVATE);

        if (isNetworkConnected()) {

            ProfileRequest request = new ProfileRequest();

            request.setId(prefs.getString(ConfigSharedPreferences.TOKEN, "token"));

            if (!request.getId().equalsIgnoreCase("token")) {

                MyRetrofitSmartFind.getInstanceSmartFind().getProfile(request).enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                        if (response.code() == 200) {

                            if (response.body().getResponseHeader().getResCode() == 200) {

                                Config.LEVEL_ACCOUNT = response.body().getResponseBody().getUser().getLevel();

                                Config.PROFILE = response.body().getResponseBody().getUser();

                                mViewModel.onNextSceen();
                            } else if (response.body().getResponseHeader().getResCode() == 404) {

                                mViewModel.onNextLogin();

                            } else {
                                mViewModel.onShowDialogMsg(mContex.getString(R.string.services_not_avail));
                            }

                        } else {

                            mViewModel.onShowDialogMsg("Vui lòng kiểm tra lại kết nối mạng rescode: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {
                        mViewModel.onShowDialogMsg("Vui lòng kiểm tra lại kết nối mạng");
                    }
                });

            } else {
                mViewModel.onNextSceen();
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
