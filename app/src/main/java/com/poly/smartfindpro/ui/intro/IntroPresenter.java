package com.poly.smartfindpro.ui.intro;

import android.content.Context;


public class IntroPresenter implements IntroContract.Presenter {
    private Context mContex;
    private IntroContract.ViewModel mViewModel;

    public IntroPresenter(Context mContex, IntroContract.ViewModel mViewModel) {
        this.mContex = mContex;
        this.mViewModel = mViewModel;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
