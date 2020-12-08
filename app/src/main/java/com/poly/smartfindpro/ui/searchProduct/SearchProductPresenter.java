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
import com.poly.smartfindpro.data.model.product.req.ProductRequest;
import com.poly.smartfindpro.data.model.product.res.Product;
import com.poly.smartfindpro.data.model.product.res.ProductResponse;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.ActivitySearchProductBinding;
import com.poly.smartfindpro.ui.listProduct.ListProductFragment;

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
        ProductRequest request = new ProductRequest();
        request.setId("5fb2073ff69b03b8f8875059");

        MyRetrofitSmartFind.getInstanceSmartFind().getAllProduct(request).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.code() == 200) {
                    mViewModel.onShow(response.body().getResponseBody().getProducts());
                    mListProduct = new ArrayList<>();
                    mListProduct.addAll(response.body().getResponseBody().getProducts());
                } else {
                    Log.d("Hihi", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

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
        Log.d("getLow", new Gson().toJson(mListAddress));

        mViewModel.onShow(mListAddress);
    }

    @Override
    public void onSearch() {
        onSearchProduct(mBinding.edtSearch.getText().toString());
        Log.d("getLow", mBinding.edtSearch.getText().toString());
    }
}
