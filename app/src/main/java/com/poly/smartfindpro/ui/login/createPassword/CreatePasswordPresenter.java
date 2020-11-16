package com.poly.smartfindpro.ui.login.createPassword;

import android.content.Context;

import java.util.ArrayList;

public class CreatePasswordPresenter implements CreatePasswordContract.Presenter {

    private Context context;

    private CreatePasswordContract.ViewModel mViewModel;

    public CreatePasswordPresenter(Context context, CreatePasswordContract.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public void OnBackClick(){

        mViewModel.OnBackClick();
    }


}
