package com.poly.smartfindpro.ui.home;

import android.content.Context;

public class HomePresenter implements HomeContract.Presenter {
    private Context mContext;
    private HomeContract.ViewModel mViewmodel;

    public HomePresenter(Context mContext, HomeContract.ViewModel mViewmodel) {
        this.mContext = mContext;
        this.mViewmodel = mViewmodel;
    }

    @Override
    public void openPost() {
        mViewmodel.openPost();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
