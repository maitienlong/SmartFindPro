package com.poly.smartfindpro.ui.searchProduct;

import android.content.Context;

import androidx.databinding.ObservableField;


public class SearchProductPresenter implements SearchProductContract.Presenter {

    private Context mContext;
    private SearchProductContract.ViewModel mViewModel;

    public ObservableField<String> title;

    public SearchProductPresenter(Context context, SearchProductContract.ViewModel viewModel) {
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
