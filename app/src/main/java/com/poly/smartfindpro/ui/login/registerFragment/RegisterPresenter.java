package com.poly.smartfindpro.ui.login.registerFragment;

import android.content.Context;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.register.regisRequest.RegisterRequest;
import com.poly.smartfindpro.data.model.register.req.CheckPhoneNumberRequest;
import com.poly.smartfindpro.data.model.register.resphonenumber.CheckPhoneResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter implements RegisterContract.Presenter {
    private Context mContex;
    private RegisterContract.ViewModel mViewmodel;
    private FragmentRegisterBinding mBinding;
    private RegisterRequest registerRequest;

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
        if (mBinding.edtAccountNumberRegister.getText().toString().equals("")) {
            mViewmodel.showMessage("vui long nhap du thong tin");
        } else {
            getCheckNum();
        }
    }

    private void getCheckNum() {
        mViewmodel.showLoading();
        CheckPhoneNumberRequest request = new CheckPhoneNumberRequest();
        request.setPhoneNumber(mBinding.edtAccountNumberRegister.getText().toString());
        MyRetrofitSmartFind.getInstanceSmartFind().getCheckNum(request).enqueue(new Callback<CheckPhoneResponse>() {
            @Override
            public void onResponse(Call<CheckPhoneResponse> call, Response<CheckPhoneResponse> response) {
                if (response.code() == 200) {
                    mViewmodel.hideLoading();
                    if (response.body().getResponseBody().getStatus().equalsIgnoreCase("Success")) {
                        registerRequest.setPhoneNumber(mBinding.edtAccountNumberRegister.getText().toString());
                        mViewmodel.checkNumber(new Gson().toJson(registerRequest), mBinding.edtAccountNumberRegister.getText().toString());
                    } else if (response.body().getResponseBody().getStatus().equalsIgnoreCase("Fail")) {
                        mViewmodel.showMessage("Số điện thoại đã được đăng ký");
                    } else {
                        mViewmodel.showMessage("Số điện thoại không chính xác");
                    }
                } else {
                    mViewmodel.hideLoading();
                    mViewmodel.showMessage(mContex.getString(R.string.services_not_avail));
                }
            }

            @Override
            public void onFailure(Call<CheckPhoneResponse> call, Throwable t) {
                mViewmodel.hideLoading();
                mViewmodel.showMessage(mContex.getString(R.string.services_not_avail));
            }
        });
    }
}
