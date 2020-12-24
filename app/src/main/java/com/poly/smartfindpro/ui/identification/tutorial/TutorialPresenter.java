package com.poly.smartfindpro.ui.identification.tutorial;

import android.content.Context;

import com.poly.smartfindpro.databinding.FragmentHomeBinding;
import com.poly.smartfindpro.ui.home.HomeContract;

public class TutorialPresenter implements TutorialContract.Presenter {

    private Context mContext;

    private TutorialContract.ViewModel mViewmodel;

    public TutorialPresenter(Context mContext, TutorialContract.ViewModel mViewmodel) {
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

    @Override
    public void onClickConfirm() {
        mViewmodel.onClickConfirm();
    }
}
