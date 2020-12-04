package com.poly.smartfindpro.ui.searchProduct;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.product.req.ProductRequest;
import com.poly.smartfindpro.data.model.product.res.ProductResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;

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
//        title = new ObservableField<>(mContext.getString(R.string.home_title_sell));
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
        ProductRequest request = new ProductRequest();
        request.setId("5fb2073ff69b03b8f8875059");

        MyRetrofitSmartFind.getInstanceSmartFind().getAllProduct(request).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.code() == 200) {
                    mViewModel.onShow(response.body().getResponseBody().getProducts());
                }else {
                    Log.d("Hihi", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });

    }
}
