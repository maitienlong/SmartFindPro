package com.poly.smartfindpro.ui.user.setting;

import android.content.Context;
import android.widget.Toast;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
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


        if (Config.isClick()) {
            mViewModel.onClickInformation();
        } else {
            mViewModel.showMessage(context.getString(R.string.pl_login));
        }
    }

    @Override
    public void onClickChangePassword() {
        if (Config.isClick()) {
            mViewModel.onClickChangePassword();
        } else {
            mViewModel.showMessage(context.getString(R.string.pl_login));
        }

    }

    @Override
    public void onClickPerMission() {
        mViewModel.onClickPerMission();
    }

    @Override
    public void onClickConfirmAccount() {
        if (Config.isClick()) {
            mViewModel.onClickConfirmAccount();
        } else {
            mViewModel.showMessage(context.getString(R.string.pl_login));
        }
    }
}
