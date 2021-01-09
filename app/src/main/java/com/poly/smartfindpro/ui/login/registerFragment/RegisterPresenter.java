package com.poly.smartfindpro.ui.login.registerFragment;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.RequiresApi;

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
                if (mBinding.edtAccountNumberRegister.length() != 0) {
                    mBinding.btnAction.setBackgroundTintList(mContex.getColorStateList(R.color.green));
                } else {
                    mBinding.btnAction.setBackgroundTintList(mContex.getColorStateList(R.color.background_button_login));
                }
            }
        };
    }

    @Override
    public void onClickRegister() {
        if (mBinding.edtAccountNumberRegister.getText().toString().equals("")) {
            mViewmodel.showMessage("Vui lòng nhập đủ thông tin");
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
