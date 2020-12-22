package com.poly.smartfindpro.ui.login;

import android.content.Context;

import com.poly.smartfindpro.databinding.ActivityLoginBinding;
import com.poly.smartfindpro.databinding.FragmentForgotPasswordBinding;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.home.HomeContract;

public class LoginPresenter implements LoginContract.ViewModel{

    private Context context;
    private LoginContract.ViewModel mViewModel;
    private ActivityLoginBinding mBinding;


    public LoginPresenter(Context context, LoginContract.ViewModel mViewModel, ActivityLoginBinding mBinding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void openForgotPassword() {
        mViewModel.openForgotPassword();
    }

//    public void
}
