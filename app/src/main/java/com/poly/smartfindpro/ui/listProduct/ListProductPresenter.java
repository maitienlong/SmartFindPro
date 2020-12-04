package com.poly.smartfindpro.ui.listProduct;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.data.model.product.Product;


public class ListProductPresenter implements ListProductContract.Presenter {

    private Context mContext;
    private ListProductContract.ViewModel mViewModel;

    public ObservableField<String> title;

    public ListProductPresenter(Context context, ListProductContract.ViewModel viewModel) {
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



    private void showData(Product mProduct){

    }
}
