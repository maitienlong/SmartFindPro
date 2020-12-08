package com.poly.smartfindpro.ui.home;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
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
        mViewmodel.openPost();
    }

    @Override
    public void onClickRentalRoom() {
        mViewmodel.onClickRentalRoom();
        getProductRental();
    }

    @Override
    public void onClickShareRoom() {
        mViewmodel.onClickShareRoom();
        getProductShare();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    private void getProductRental(){
        HomeRequest request = new HomeRequest();
        request.setId("5fb2073ff69b03b8f8875059");

        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if (response.code() == 200) {
                    productsList = new ArrayList<>();
                    for (int i=0; i< response.body().getResponseBody().getProducts().size(); i++){
                        if (response.body().getResponseBody().getProducts().get(i).getProduct().getCategory().equals("Nhà trọ")
                                || response.body().getResponseBody().getProducts().get(i).getProduct().getCategory().equals("Chung cư")
                                || response.body().getResponseBody().getProducts().get(i).getProduct().getCategory().equals("Nguyên căn")){
                            productsList.add(response.body().getResponseBody().getProducts().get(i));
                        }
                    }
                    Log.d("list111", "onResponse: " + productsList.size());
                    mViewmodel.onShow(productsList);

                } else {

                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {

            }
        });
    }

    private void getProductShare(){
        HomeRequest request = new HomeRequest();
        request.setId("5fb2073ff69b03b8f8875059");

        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if (response.code() == 200) {
                    productsList = new ArrayList<>();
                    for (int i=0; i< response.body().getResponseBody().getProducts().size(); i++){
                        if (response.body().getResponseBody().getProducts().get(i).getProduct().getCategory().equals("Ở ghép")){
                            productsList.add(response.body().getResponseBody().getProducts().get(i));
                        }
                    }
                    Log.d("list111", "onResponse: " + productsList.size());
                    mViewmodel.onShow(productsList);

                } else {

                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {

            }
        });
    }


//    private void getProduct() {
//        ProductRequest request = new ProductRequest();
//        request.setId("5fb2073ff69b03b8f8875059");
//
//        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<ProductResponse>() {
//            @Override
//            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
//                if (response.code() == 200) {
////                    if (response.body().getResponseBody().getProducts())
//                    mViewmodel.onShow(response.body().getResponseBody().getProducts());
//
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProductResponse> call, Throwable t) {
//
//            }
//        });
//    }
}
