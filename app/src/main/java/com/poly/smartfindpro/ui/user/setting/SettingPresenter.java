package com.poly.smartfindpro.ui.user.setting;

import android.content.Context;
import android.widget.Toast;

import com.poly.smartfindpro.ui.user.userFragment.UserContact;

public class SettingPresenter implements SettingContact.Presenter {
    private Context context;
    private SettingContact.ViewModel mViewModel;

    public SettingPresenter(Context context, SettingContact.ViewModel mViewModel) {
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
    public void onClickInformation() {

        mViewModel.onClickInformation();

    }

    @Override
    public void onClickChangePassword() {
        mViewModel.onClickChangePassword();
    }

    @Override
    public void onClickPerMission() {
        mViewModel.onClickPerMission();
    }
}
