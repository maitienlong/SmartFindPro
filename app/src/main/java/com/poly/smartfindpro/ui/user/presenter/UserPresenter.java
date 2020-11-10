package com.poly.smartfindpro.ui.user.presenter;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.ui.user.constract.UserContract;


public class UserPresenter implements UserContract.Presenter {

    private Context mContext;
    private UserContract.ViewModel mViewModel;
    private final Context mContext;
    private final MainContract.ViewModel mViewModel;

    public ObservableField<String> title;

    public UserPresenter(Context context, UserContract.ViewModel viewModel) {
        mContext = context;
        mViewModel = viewModel;

        initData();
    }

    private void initData() {
      //  title = new ObservableField<>(mContext.getString(R.string.home_title_sell));
        mViewModel.openFragment();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
