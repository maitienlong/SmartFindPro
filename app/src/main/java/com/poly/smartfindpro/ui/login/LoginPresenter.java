package com.poly.smartfindpro.ui.login;

import android.content.Context;

public class LoginPresenter implements LoginContract.Presenter {

    private Context mContex;
    private LoginContract.ViewModel mViewmodel;

    public LoginPresenter(Context mContex, LoginContract.ViewModel mViewmodel) {
        this.mContex = mContex;
        this.mViewmodel = mViewmodel;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public void onClickForgotPassword(){
        mViewmodel.onForgotClick();
    }
}
