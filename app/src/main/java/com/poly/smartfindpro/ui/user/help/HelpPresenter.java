package com.poly.smartfindpro.ui.user.help;

import android.content.Context;

import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.ui.user.setting.information.InforContact;

public class HelpPresenter implements HelpContact.Presenter {
    private Context context;

    private HelpContact.ViewModel mViewModel;
    private ProfileResponse mProfile;

    public HelpPresenter(Context context, HelpContact.ViewModel mViewModel) {
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
