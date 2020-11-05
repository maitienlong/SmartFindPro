package com.poly.smartfindpro.ui.map;

import android.content.Context;

import androidx.databinding.ObservableField;


public class MapPresenter implements MapContract.Presenter {

    private Context mContext;
    private MapContract.ViewModel mViewModel;

    public ObservableField<String> title;

    public MapPresenter(Context context, MapContract.ViewModel viewModel) {
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
