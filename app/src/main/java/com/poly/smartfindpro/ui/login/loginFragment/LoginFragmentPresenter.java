package com.poly.smartfindpro.ui.login.loginFragment;

import android.content.Context;

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

    public void sendRequestUser() {

        if (mBinding.edtAccountNumber.getText().toString().equals("")) {
            mViewmodel.showMessage("Vui lòng nhập tài khoản");
        } else if (mBinding.edtPassword.getText().toString().equals("")) {
            mViewmodel.showMessage("Vui lòng nhập Mật khẩu");
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
                    Config.PROFILE = response.body().getResponseBody();
                    Config.TOKEN_USER = token;

                    mViewmodel.saveLogin(username, password, token);
                } else {
                    mViewmodel.hideLoading();
                    mViewmodel.showMessage("Tài khoản hoặc mật khẩu không chính xác");
                }

            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mViewmodel.hideLoading();
            }
        });


    }

}
