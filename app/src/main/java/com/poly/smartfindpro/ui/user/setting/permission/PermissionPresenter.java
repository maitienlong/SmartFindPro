package com.poly.smartfindpro.ui.user.setting.permission;

import android.content.Context;

public class PermissionPresenter implements PermissionContact.Presenter {

    private Context context;
    private PermissionContact.ViewModel mViewModel;

    public PermissionPresenter(Context context, PermissionContact.ViewModel mViewModel) {
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
