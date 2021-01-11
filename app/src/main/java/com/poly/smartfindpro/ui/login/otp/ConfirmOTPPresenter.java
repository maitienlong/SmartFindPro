package com.poly.smartfindpro.ui.login.otp;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.RequiresApi;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.databinding.FragmentConfirmOtpBinding;
import com.poly.smartfindpro.databinding.FragmentRegisterBinding;
import com.poly.smartfindpro.ui.login.registerFragment.RegisterContract;

public class ConfirmOTPPresenter implements ConfirmOTPContract.Presenter {
    private Context mContex;
    private ConfirmOTPContract.ViewModel mViewmodel;
    private FragmentConfirmOtpBinding mBinding;

    public ConfirmOTPPresenter(Context mContex, ConfirmOTPContract.ViewModel mViewmodel, FragmentConfirmOtpBinding mBinding) {
        this.mContex = mContex;
        this.mViewmodel = mViewmodel;
        this.mBinding = mBinding;
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
                if (mBinding.edtOtp.length() != 0) {
                    mBinding.btnAction.setBackgroundTintList(mContex.getColorStateList(R.color.green));
                } else {
                    mBinding.btnAction.setBackgroundTintList(mContex.getColorStateList(R.color.background_button_login));
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

    @Override
    public void onClickConfirm() {
        mViewmodel.onClickConfirm();
    }

    @Override
    public void onResendOTP() {
        mViewmodel.onResendOTP();
    }

}
