package com.poly.smartfindpro.ui.login.otp;

import android.content.Context;

import com.poly.smartfindpro.databinding.FragmentConfirmOtpBinding;
import com.poly.smartfindpro.databinding.FragmentRegisterBinding;
import com.poly.smartfindpro.ui.login.registerFragment.RegisterContract;

public class ConfirmOTPPresenter implements ConfirmOTPContract.Presenter {
    private Context mContex;
    private ConfirmOTPContract.ViewModel mViewmodel;
      private FragmentConfirmOtpBinding mBinding;

    public ConfirmOTPPresenter(Context mContex, ConfirmOTPContract.ViewModel mViewmodel,FragmentConfirmOtpBinding mBinding) {
        this.mContex = mContex;
        this.mViewmodel = mViewmodel;
        this.mBinding =mBinding;
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
}
