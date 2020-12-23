package com.poly.smartfindpro.ui.login.forgotPassword;

import android.content.Context;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.register.req.CheckPhoneNumberRequest;
import com.poly.smartfindpro.data.model.register.resphonenumber.CheckPhoneResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentForgotPasswordBinding;
import com.poly.smartfindpro.ui.login.createPassword.CreatePasswordContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {

    private Context context;
    private ForgotPasswordContract.ViewModel mViewModel;
    private FragmentForgotPasswordBinding mBinding;


    public ForgotPasswordPresenter(Context context, ForgotPasswordContract.ViewModel mViewModel, FragmentForgotPasswordBinding mBinding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
    }

    @Override
    public void OnBackClick() {
        mViewModel.OnBackClick();
    }


    @Override
    public void onClickForgot() {
        if (mBinding.edtPhoneNummber.getText().toString().equals("")) {
            mViewModel.showMessage("Vui lòng nhập số điện thoại");
        } else {
            getCheckNum();
        }
    }

    private void getCheckNum() {
        mViewModel.showLoading();
        CheckPhoneNumberRequest request = new CheckPhoneNumberRequest();
        request.setPhoneNumber(mBinding.edtPhoneNummber.getText().toString());
        MyRetrofitSmartFind.getInstanceSmartFind().getCheckNum(request).enqueue(new Callback<CheckPhoneResponse>() {
            @Override
            public void onResponse(Call<CheckPhoneResponse> call, Response<CheckPhoneResponse> response) {
                if (response.code() == 200) {
                    mViewModel.hideLoading();
                    if (response.body().getResponseBody().getStatus().equalsIgnoreCase("Success")) {
//                        registerRequest.setPhoneNumber(mBinding.edtAccountNumberRegister.getText().toString());
                        mViewModel.checkData(mBinding.edtPhoneNummber.getText().toString());
                    } else if (response.body().getResponseBody().getStatus().equalsIgnoreCase("Fail")) {
//                        mViewModel.showMessage("Số điện thoại đã được đăng ký");
                    } else {
                        mViewModel.showMessage("Số điện thoại không chính xác");
                    }
                } else {
                    mViewModel.hideLoading();
                    mViewModel.showMessage(context.getString(R.string.services_not_avail));
                }
            }

            @Override
            public void onFailure(Call<CheckPhoneResponse> call, Throwable t) {
                mViewModel.hideLoading();
                mViewModel.showMessage(context.getString(R.string.services_not_avail));
            }
        });
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
