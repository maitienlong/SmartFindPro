package com.poly.smartfindpro.ui.login.createPassword;

import android.content.Context;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.register.regisRequest.RegisterRequest;
import com.poly.smartfindpro.data.model.register.regisRes.RegisterResponse;
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

        MyRetrofitSmartFind.getInstanceSmartFind().getRegister(registerRequest).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.code() == 200 && response.body().getResponseHeader().getResMessage().equals("Succsess")) {
                    mViewModel.hideLoading();
                    mViewModel.onGoToLogin(registerRequest.getPhoneNumber());
                } else {
                    mViewModel.hideLoading();
                    mViewModel.showMessage("Tài khoản đã được đăng ký trước đó");
                }

            }


            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                mViewModel.hideLoading();
                mViewModel.showMessage(context.getString(R.string.services_not_avail));
            }
        });
    }


}
