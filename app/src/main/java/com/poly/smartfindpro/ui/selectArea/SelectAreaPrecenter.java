package com.poly.smartfindpro.ui.selectArea;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.ui.MainContract;

public class SelectAreaPrecenter implements SelectAreaContract.Presenter {


    private Context mContext;
    private SelectAreaContract.ViewModel mViewModel;

    public ObservableField<String> title;

    public SelectAreaPrecenter(Context context, SelectAreaContract.ViewModel viewModel) {
        mContext = context;
        mViewModel = viewModel;

        initData();
    }

    private void initData() {
        //  title = new ObservableField<>(mContext.getString(R.string.home_title_sell));
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
