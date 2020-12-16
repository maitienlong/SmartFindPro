package com.poly.smartfindpro.ui.post.postsuccess;

import android.content.Context;

public class SuccessPostPresenter implements SuccessPostContract.Presenter {
    private Context context;

    private SuccessPostContract.ViewModel mViewModel;


    public SuccessPostPresenter(Context context, SuccessPostContract.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
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
    public void onConfirm() {
        mViewModel.onConfirm();
    }

    @Override
    public void onNext() {

    }
}
