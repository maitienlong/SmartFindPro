package com.poly.smartfindpro.ui.searchProduct.filterProduct;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.data.model.home.req.HomeRequest;
import com.poly.smartfindpro.data.model.home.res.HomeResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.ActivityFilterProductBinding;
import com.poly.smartfindpro.databinding.ActivitySearchProductBinding;
import com.poly.smartfindpro.ui.searchProduct.SearchProductContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterProductPresenter implements FilterProductContact.Presenter {
    private Context mContext;
    private FilterProductContact.ViewModel mViewModel;
    private ActivityFilterProductBinding mBinding;

    public FilterProductPresenter(Context mContext, FilterProductContact.ViewModel mViewModel,ActivityFilterProductBinding mBinding) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
        initData();
    }

    private void initData() {
        getProduct();
    }

    private void getProduct() {
        HomeRequest request = new HomeRequest();
        request.setId("5fb2073ff69b03b8f8875059");

        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if (response.code() == 200) {
                    mViewModel.onShow(response.body().getResponseBody().getProducts());

                } else {

                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClickFilter() {
        mViewModel.onClickFilter();
    }
}
