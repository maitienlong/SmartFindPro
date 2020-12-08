package com.poly.smartfindpro.ui.searchProduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.req.ProductRequest;
import com.poly.smartfindpro.data.model.product.res.Product;
import com.poly.smartfindpro.data.model.product.res.ProductResponse;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.ActivitySearchProductBinding;
import com.poly.smartfindpro.ui.listProduct.ListProductFragment;
import com.poly.smartfindpro.ui.searchProduct.filterProduct.FilterProductActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchProductPresenter implements SearchProductContract.Presenter {

    private Context mContext;
    private SearchProductContract.ViewModel mViewModel;

    public ObservableField<String> title;

    public ObservableField<String> key;

    private List<Products> mListProduct;

    private ActivitySearchProductBinding mBinding;

    public SearchProductPresenter(Context context, SearchProductContract.ViewModel viewModel, ActivitySearchProductBinding binding) {
        mContext = context;
        mViewModel = viewModel;
        this.mBinding = binding;
        initData();
    }

    private void initData() {
        key = new ObservableField<>();
        getProduct();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public void getProduct() {
        mViewModel.showLoading();

        ProductRequest request = new ProductRequest();

        request.setId(Config.TOKEN_USER);

        MyRetrofitSmartFind.getInstanceSmartFind().getAllProduct(request).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.code() == 200) {
                    mViewModel.hideLoading();
                    mViewModel.onShow(response.body().getResponseBody().getProducts());
                    mListProduct = new ArrayList<>();
                    mListProduct.addAll(response.body().getResponseBody().getProducts());
                } else {
                    mViewModel.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                mViewModel.hideLoading();
            }
        });
    }

    private void onSearchProduct(String key) {
        List<Products> mListAddress = new ArrayList<>();

        for (Products item : mListProduct) {
            if (item.getAddress().getDetailAddress().toLowerCase().contains(key) || item.getAddress().getCommuneWardTown().toLowerCase().contains(key)) {
                mListAddress.add(item);
            }
        }

        if (mListProduct != null && !mListProduct.isEmpty()) {
            mViewModel.onShow(mListAddress);
        }

    }

    @Override
    public void onSearch() {
        onSearchProduct(mBinding.edtSearch.getText().toString());
    }
}
