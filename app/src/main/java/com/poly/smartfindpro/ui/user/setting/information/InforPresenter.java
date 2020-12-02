package com.poly.smartfindpro.ui.user.setting.information;

import android.content.Context;

import com.poly.smartfindpro.ui.user.setting.SettingContact;

public class InforPresenter implements InforContact.Presenter {

    private Context context;
    private InforContact.ViewModel mViewModel;

    public InforPresenter(Context context, InforContact.ViewModel mViewModel) {
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
    public void onBackClick() {
        mViewModel.onBackClick();
    }
}
