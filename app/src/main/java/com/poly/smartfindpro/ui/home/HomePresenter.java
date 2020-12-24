package com.poly.smartfindpro.ui.home;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.home.req.HomeRequest;
import com.poly.smartfindpro.data.model.home.res.HomeResponse;
import com.poly.smartfindpro.data.model.home.res.Product;
import com.poly.smartfindpro.data.model.product.req.ProductRequest;
import com.poly.smartfindpro.data.model.product.res.ProductResponse;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter {
    private Context mContext;
    private HomeContract.ViewModel mViewmodel;
    private FragmentHomeBinding mBinding;
    private List<Product> productsList;

    public HomePresenter(Context mContext, HomeContract.ViewModel mViewmodel, FragmentHomeBinding mBinding) {
        this.mContext = mContext;
        this.mViewmodel = mViewmodel;
        this.mBinding = mBinding;
        initData();
    }

    private void initData() {

//        getProduct();
        getProductRental();
    }

    @Override
    public void openPost() {
        switch (Config.LEVEL_ACCOUNT) {
            case 0:
                mViewmodel.showMessage(mContext.getString(R.string.msg_đinhanh));
                break;
            case 1:
                mViewmodel.showMessage(mContext.getString(R.string.msg_đinhanh));
                break;
            case 2:
                mViewmodel.showMessagePost("Tài khoản của bạn bị giới hạn lượt đăng là 3 bài/ngày. Vui lòng nâng cấp tài khoản để trải nghiệm ứng dụng tốt hơn");
                break;
            case 3:
                mViewmodel.openPost();
                break;
        }

    }

    @Override
    public void onClickRentalRoom() {
        mViewmodel.onCheckStatus(1);
        getProductRental();
    }

    @Override
    public void onClickShareRoom() {
        mViewmodel.onCheckStatus(2);
        getProductShare();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    private void getProductRental() {
        HomeRequest request = new HomeRequest();
        request.setId(Config.TOKEN_USER);
        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getResponseBody() != null) {
                        productsList = new ArrayList<>();

                        for (Product item : response.body().getResponseBody().getProducts()) {
                            if (!item.getProduct().getCategory().toLowerCase().contains("ở ghép")) {
                                productsList.add(item);
                            }
                        }
                        mViewmodel.onShow(productsList);
                    } else {
                        mViewmodel.showMessage(response.body().getResponseHeader().getResCode() + " : " + response.body().getResponseHeader().getResMessage());
                    }

                } else {
                    mViewmodel.showMessage(mContext.getString(R.string.services_not_avail));
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                mViewmodel.showMessage(mContext.getString(R.string.services_not_avail) + t.toString());
            }
        });
    }


    private void getProductShare() {
        mViewmodel.showLoading();
        HomeRequest request = new HomeRequest();
        request.setId(Config.TOKEN_USER);

        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if (response.code() == 200) {
                    mViewmodel.hideLoading();

                    productsList = new ArrayList<>();

                    for (Product item : response.body().getResponseBody().getProducts()) {
                        if (item.getProduct().getCategory().toLowerCase().contains("ở ghép")) {
                            productsList.add(item);
                        }
                    }

                    mViewmodel.onShow(productsList);


                } else {
                    mViewmodel.hideLoading();

                    mViewmodel.showMessage(mContext.getString(R.string.services_not_avail));
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                mViewmodel.hideLoading();
                mViewmodel.showMessage(mContext.getString(R.string.services_not_avail));
            }
        });
    }


}
