package com.poly.smartfindpro.ui.home;

import android.content.Context;

import com.poly.smartfindpro.data.model.home.req.HomeRequest;
import com.poly.smartfindpro.data.model.home.res.HomeResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentHomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter {
    private Context mContext;
    private HomeContract.ViewModel mViewmodel;
    private FragmentHomeBinding mBinding;

    public HomePresenter(Context mContext, HomeContract.ViewModel mViewmodel, FragmentHomeBinding mBinding) {
        this.mContext = mContext;
        this.mViewmodel = mViewmodel;
        this.mBinding = mBinding;
        initData();
    }
    private void initData() {

        getProduct();
    }
    @Override
    public void openPost() {
        mViewmodel.openPost();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
    private void getProduct() {
        HomeRequest request = new HomeRequest();
        request.setId("5fb2073ff69b03b8f8875059");

        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if (response.code() == 200) {
                    mViewmodel.onShow(response.body().getResponseBody().getProducts());

                } else {

                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {

            }
        });
    }
}
