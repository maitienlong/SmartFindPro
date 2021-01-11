package com.poly.smartfindpro.ui.checklevel.levelvang;

import android.content.Context;

public class RankVangPresenter implements RankVangContract.Presenter {

    private Context mContext;

    private RankVangContract.ViewModel mViewmodel;

    public RankVangPresenter(Context mContext, RankVangContract.ViewModel mViewmodel) {
        this.mContext = mContext;
        this.mViewmodel = mViewmodel;
        initData();
    }

    private void initData() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

}
