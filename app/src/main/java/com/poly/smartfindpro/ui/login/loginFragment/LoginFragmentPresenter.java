package com.poly.smartfindpro.ui.login.loginFragment;

import android.content.Context;

import com.poly.smartfindpro.data.Config;

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

    public void sendRequestUser() {
        // ok
        mViewmodel.saveLogin(username, password, token);
        String token = "5fb2073ff69b03b8f8875059";
        Config.TOKEN_USER = token;
    }
}
