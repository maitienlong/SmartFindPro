package com.poly.smartfindpro.ui.checklevel.leveldong;

import android.content.Context;

import androidx.databinding.ObservableField;

public class RankDongPresenter implements RankDongContract.Presenter {

    private Context mContext;

    private RankDongContract.ViewModel mViewmodel;

    public RankDongPresenter(Context mContext, RankDongContract.ViewModel mViewmodel) {
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
