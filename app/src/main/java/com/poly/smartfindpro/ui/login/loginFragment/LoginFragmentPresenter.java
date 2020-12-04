package com.poly.smartfindpro.ui.login.loginFragment;

import android.content.Context;

public class LoginFragmentPresenter implements LoginFragmentContract.Presenter {
    private Context mContex;
    private LoginFragmentContract.ViewModel mViewmodel;

    private String username, password, token;

    public LoginFragmentPresenter(Context mContex, LoginFragmentContract.ViewModel mViewmodel) {
        this.mContex = mContex;
        this.mViewmodel = mViewmodel;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public void sendRequestUser(){
        // ok
        mViewmodel.saveLogin(username, password, token);
    }
}
