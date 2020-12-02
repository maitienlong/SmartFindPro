package com.poly.smartfindpro.ui.user.setting.changepass;

import android.content.Context;

import com.poly.smartfindpro.ui.user.setting.information.InforContact;

public class ChangePassPresenter implements ChangePassContact.Presenter {
    private Context context;
    private ChangePassContact.ViewModel mViewModel;

    public ChangePassPresenter(Context context, ChangePassContact.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
    }

    @Override
    public void onBackClick() {
        mViewModel.onBackClick();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
