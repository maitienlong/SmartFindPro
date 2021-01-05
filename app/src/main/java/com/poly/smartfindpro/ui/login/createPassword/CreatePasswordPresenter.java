package com.poly.smartfindpro.ui.login.createPassword;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.register.regisRequest.RegisterRequest;
import com.poly.smartfindpro.data.model.register.regisRes.RegisterResponse;
import com.poly.smartfindpro.data.model.register.resphonenumber.CheckPhoneResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentCreatePasswordBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePasswordPresenter implements CreatePasswordContract.Presenter {

    private Context context;

    private CreatePasswordContract.ViewModel mViewModel;
    private FragmentCreatePasswordBinding mBinding;

    private RegisterRequest registerRequest;

    public CreatePasswordPresenter(Context context, CreatePasswordContract.ViewModel mViewModel, FragmentCreatePasswordBinding mBinding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
        registerRequest = new RegisterRequest();
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
                if (mBinding.edtPassword.length() != 0 && mBinding.edtConfirmPassword.length() != 0) {
                    mBinding.btnAction.setBackgroundTintList(context.getColorStateList(R.color.green));
                } else {
                    mBinding.btnAction.setBackgroundTintList(context.getColorStateList(R.color.background_button_login));
                }
            }
        };
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public void setRegisterRequest(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
    }

    public void OnBackClick() {
//registerRequest.setPassword();
        mViewModel.OnBackClick();
    }

    @Override
    public void onClickRegister() {
        if (mBinding.edtPassword.getText().toString().equals("") && mBinding.edtConfirmPassword.getText().toString().equals("")) {
            mViewModel.showMessage("Vui lòng nhập đủ thông tin");

        } else {
            getRegister();
        }

    }

    private void getRegister() {
        mViewModel.showLoading();
        registerRequest.setPassword(mBinding.edtPassword.getText().toString());
        registerRequest.setFullName(registerRequest.getPhoneNumber());

        MyRetrofitSmartFind.getInstanceSmartFind().getRegister(registerRequest).enqueue(new Callback<CheckPhoneResponse>() {
            @Override
            public void onResponse(Call<CheckPhoneResponse> call, Response<CheckPhoneResponse> response) {
                Log.d("CheckCreate", "onResponse: ");
                if (response.code() == 200) {
                    mViewModel.hideLoading();
                    if (response.body().getResponseHeader().getResCode() == 200) {
                        if (response.body().getResponseBody().getStatus().equalsIgnoreCase("success")) {
                            mViewModel.hideLoading();
                            mViewModel.onGoToLogin(registerRequest.getPhoneNumber());
                        } else {

                            mViewModel.showMessage("Tài khoản đã được đăng ký trước đó");
                        }
                    } else {

                        mViewModel.showMessage(context.getString(R.string.services_not_avail));
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

}
