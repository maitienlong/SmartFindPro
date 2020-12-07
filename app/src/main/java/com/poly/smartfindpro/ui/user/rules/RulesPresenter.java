package com.poly.smartfindpro.ui.user.rules;

import android.content.Context;

import com.poly.smartfindpro.ui.user.profile.ProfileContact;

public class RulesPresenter implements RulesContact.Presenter {

    private Context context;
    private RulesContact.ViewModel mViewModel;

    public RulesPresenter(Context context, RulesContact.ViewModel mViewModel) {
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
