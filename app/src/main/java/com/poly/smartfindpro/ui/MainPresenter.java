package com.poly.smartfindpro.ui;

import android.content.Context;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;


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

        if (Config.isClick()) {
            mViewModel.onSelecFind();
        } else {
            mViewModel.showMessage(mContex.getString(R.string.pl_login));
        }
    }

    @Override
    public void onSelectMessager() {


        if (Config.isClick()) {
            mViewModel.onSelectMessager();
        } else {
            mViewModel.showMessage(mContex.getString(R.string.pl_login));
        }
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
