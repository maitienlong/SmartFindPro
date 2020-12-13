package com.poly.smartfindpro.ui.login.registerFragment;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.login.req.LoginRequest;
import com.poly.smartfindpro.data.model.login.res.LoginResponse;
import com.poly.smartfindpro.data.model.register.regisRequest.RegisterRequest;
import com.poly.smartfindpro.data.model.register.req.CheckPhoneNumberRequest;
import com.poly.smartfindpro.data.model.register.res.CheckPhoneNumberResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.databinding.FragmentRegisterBinding;
import com.poly.smartfindpro.ui.login.loginFragment.LoginFragmentContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter implements RegisterContract.Presenter {
    private Context mContex;
    private RegisterContract.ViewModel mViewmodel;
    private CheckPhoneNumberResponse mCheck;
    private FragmentRegisterBinding mBinding;

    private RegisterRequest registerRequest;
    String phoneNumber;

    public RegisterPresenter(Context mContex, RegisterContract.ViewModel mViewmodel, FragmentRegisterBinding mBinding) {
        this.mContex = mContex;
        this.mViewmodel = mViewmodel;
        this.mBinding = mBinding;
        registerRequest = new RegisterRequest();
    }



    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void onClickRegister() {
       if (mBinding.edtAccountNumberRegister.getText().toString().equals("")){
           mViewmodel.showMessage("vui long nhap du thong tin");
       }else {
           getCheckNum();
       }
    }

    private void getCheckNum() {
        mViewmodel.showLoading();
        CheckPhoneNumberRequest request = new CheckPhoneNumberRequest();
        request.setPhoneNumber(mBinding.edtAccountNumberRegister.getText().toString());
        MyRetrofitSmartFind.getInstanceSmartFind().getCheckNum(request).enqueue(new Callback<CheckPhoneNumberResponse>() {
            @Override
            public void onResponse(Call<CheckPhoneNumberResponse> call, Response<CheckPhoneNumberResponse> response) {

                if (response.code() == 200 && response.body().getResponseHeader().getResMessage().equals("Succsess")) {
                    mViewmodel.hideLoading();
                    Log.d("checkNum", String.valueOf(response.body().getResponseHeader().getResMessage()));
                    registerRequest.setPhoneNumber(mBinding.edtAccountNumberRegister.getText().toString());
                    mViewmodel.checkNumber(new Gson().toJson(registerRequest), mBinding.edtAccountNumberRegister.getText().toString());
                }else if(response.code() == 200 && response.body().getResponseHeader().getResMessage().equals("Fail")){
                    mViewmodel.hideLoading();
                    mViewmodel.showMessage("Số điện thoại đã được đăng ký");
                }else {
                    mViewmodel.hideLoading();
                    mViewmodel.showMessage("Số điện thoại không chính xác");
                }

            }


            @Override
            public void onFailure(Call<CheckPhoneNumberResponse> call, Throwable t) {
                mViewmodel.hideLoading();
            }
        });
    }
}
