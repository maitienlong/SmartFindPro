package com.poly.smartfindpro.ui;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.R;


public class MainPresenter implements MainContract.Presenter {

    private Context mContext;
    private MainContract.ViewModel mViewModel;

    public ObservableField<String> title;

    public MainPresenter(Context context, MainContract.ViewModel viewModel) {
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
