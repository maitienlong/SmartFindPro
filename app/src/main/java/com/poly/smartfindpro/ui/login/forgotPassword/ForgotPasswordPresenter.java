package com.poly.smartfindpro.ui.login.forgotPassword;

import android.content.Context;

import com.poly.smartfindpro.ui.login.createPassword.CreatePasswordContract;

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {

    private Context context;

    private ForgotPasswordContract.ViewModel mViewModel;

    public ForgotPasswordPresenter(Context context, ForgotPasswordContract.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void OnBackClick() {
        mViewModel.OnBackClick();
    }
}
