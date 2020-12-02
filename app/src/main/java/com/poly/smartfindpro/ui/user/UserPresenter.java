package com.poly.smartfindpro.ui.user;

import android.content.Context;

import androidx.databinding.ObservableField;


public class UserPresenter implements UserContract.Presenter {

    private Context mContext;
    private UserContract.ViewModel mViewModel;

    public ObservableField<String> title;

    public UserPresenter(Context context, UserContract.ViewModel viewModel) {
        mContext = context;
        mViewModel = viewModel;

        initData();
    }

    private void initData() {
      //  title = new ObservableField<>(mContext.getString(R.string.home_title_sell));
//        mViewModel.openFragment();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
