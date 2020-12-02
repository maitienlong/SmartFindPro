package com.poly.smartfindpro.ui.user.profile;

import android.content.Context;

public class ProfilePresenter implements ProfileContact.Presenter {

    private Context context;
    private ProfileContact.ViewModel mViewModel;

    public ProfilePresenter(Context context, ProfileContact.ViewModel mViewModel) {
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

    @Override
    public void onClickEditUser() {
        mViewModel.onClickEditUser();
    }
}
