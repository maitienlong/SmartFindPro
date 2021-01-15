package com.poly.smartfindpro.ui.login.forgotPassword.recreatePassword;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.forgotpasswrd.ForgotPasswordRequest;
import com.poly.smartfindpro.data.model.product.deleteProduct.req.res.DeleteProductResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentRecreatePasswordBinding;
import com.poly.smartfindpro.ui.login.forgotPassword.ForgotPasswordContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReCreatePasswordPresenter implements ReCreatePasswordContract.Presenter {
    private Context context;

    private ReCreatePasswordContract.ViewModel mViewModel;

    private FragmentRecreatePasswordBinding mBinding;

    private String phoneNumber;

    public ReCreatePasswordPresenter(Context context, ReCreatePasswordContract.ViewModel mViewModel, FragmentRecreatePasswordBinding mBinding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void OnBackClick() {
        mViewModel.OnBackClick();
    }

    public void onCreatePass() {
        if (mBinding.edtOtp.getText().toString().trim().length() != 6) {
            mViewModel.showMessage("Mã OTP không hợp lệ");
        } else if (mBinding.edtCreatePassword.getText().toString().trim().length() > 7) {
            mViewModel.showMessage("Mật khẩu ít nhất 8 kỹ tự");
        } else if (!mBinding.edtRecreatePassword.getText().toString().trim().equals(mBinding.edtCreatePassword.getText().toString().trim())) {
            mViewModel.showMessage("Mật khẩu không khớp");
        } else {
            mViewModel.showLoading();
            ForgotPasswordRequest request = new ForgotPasswordRequest();
            request.setPhone_number(phoneNumber);
            request.setPassword(mBinding.edtCreatePassword.getText().toString().trim());

            MyRetrofitSmartFind.getInstanceSmartFind().forgotPassword(request).enqueue(new Callback<DeleteProductResponse>() {
                @Override
                public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                    mViewModel.hideLoading();
                    if (response.code() == 200) {
                        if (response.body().getResponseHeader().getResCode() == 200) {
                            mViewModel.onSuccess();
                        } else {
                            mViewModel.showMessage(response.body().getResponseHeader().getResMessage());
                        }
                    }else{
                        mViewModel.showMessage(context.getString(R.string.services_not_avail));
                    }
                }

                @Override
                public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                    mViewModel.hideLoading();
                    mViewModel.showMessage(context.getString(R.string.services_not_avail));
                }
            });
        }
    }

}
