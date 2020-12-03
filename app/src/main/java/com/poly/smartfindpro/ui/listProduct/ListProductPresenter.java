package com.poly.smartfindpro.ui.listProduct;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.poly.smartfindpro.data.model.product.Product;
import com.poly.smartfindpro.data.retrofit.RetrofitConnect;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
