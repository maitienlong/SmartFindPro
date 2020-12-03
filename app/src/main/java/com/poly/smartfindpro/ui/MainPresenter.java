package com.poly.smartfindpro.ui;

import android.content.Context;


public class MainPresenter implements MainContract.Presenter {
    private Context mContex;
    private MainContract.ViewModel mViewModel;

    public MainPresenter(Context mContex, MainContract.ViewModel mViewModel) {
        this.mContex = mContex;
        this.mViewModel = mViewModel;
    }

    @Override
    public void onSelectHome() {
        mViewModel.onSelectHome();
    }

    @Override
    public void onSelecFind() {
        mViewModel.onSelecFind();
    }

    @Override
    public void onSelectMessager() {
        mViewModel.onSelectMessager();
    }

    @Override
    public void onSelectUser() {
        mViewModel.onSelectUser();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
