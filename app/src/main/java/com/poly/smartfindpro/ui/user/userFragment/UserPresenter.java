package com.poly.smartfindpro.ui.user.userFragment;

import android.content.Context;

public class UserPresenter implements UserContact.Presenter {
    private Context context;
    private UserContact.ViewModel mViewModel;

    public UserPresenter(Context context, UserContact.ViewModel mViewModel) {
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
    public void onClickProfile() {
        mViewModel.onClickProfile();
    }

    @Override
    public void onClickSetting() {
        mViewModel.onClickSetting();
    }

    @Override
    public void onClickRules() {
        mViewModel.onClickRules();
    }

    @Override
    public void onClickHelp() {
        mViewModel.onClickHelp();
    }

    @Override
    public void onClickLogOut() {
        mViewModel.onClickLogOut();
    }
}
