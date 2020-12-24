package com.poly.smartfindpro.ui.checklevel.levelbac;

import android.content.Context;

public class RankBacPresenter implements RankBacContract.Presenter {

    private Context mContext;

    private RankBacContract.ViewModel mViewmodel;

    public RankBacPresenter(Context mContext, RankBacContract.ViewModel mViewmodel) {
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
