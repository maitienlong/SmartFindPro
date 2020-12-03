package com.poly.smartfindpro.ui.searchProduct;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.poly.smartfindpro.data.model.product.Product;
import com.poly.smartfindpro.data.retrofit.RetrofitConnect;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        getProduct();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public void getProduct() {
        RetrofitConnect.getInstanceSmartFind().getAllProduct().enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.code() != 200) {
                    Log.d("CheckResponse", "Check connect" + response.errorBody());
                    return;
                }
                Log.d("CheckResponse", new Gson().toJson(response.body()));
                mViewModel.onShow(response.body().getResponse().getProducts());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

    }
}
