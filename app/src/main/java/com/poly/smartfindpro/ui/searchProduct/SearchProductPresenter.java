package com.poly.smartfindpro.ui.searchProduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.req.ProductRequest;
import com.poly.smartfindpro.data.model.product.res.Location;
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

    public ObservableField<String> hint;

    public ObservableField<String> key;

    private List<Products> mListProduct;

    private ActivitySearchProductBinding mBinding;

    private int type = 1;

    public SearchProductPresenter(Context context, SearchProductContract.ViewModel viewModel, ActivitySearchProductBinding binding) {
        mContext = context;
        mViewModel = viewModel;
        this.mBinding = binding;
        initData();
    }

    private void initData() {
        key = new ObservableField<>();
        hint = new ObservableField<>();
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

    private void onSearchProduct(int type, String key) {
        if (type == 1) {
            List<Products> mListAddress = new ArrayList<>();

            for (Products item : mListProduct) {
                String address = item.getAddress().getDetailAddress() + ", " + item.getAddress().getCommuneWardTown() + ", " + item.getAddress().getDistrictsTowns() + ", " + item.getAddress().getProvinceCity();
                if (address.toLowerCase().contains(key)) {
                    mListAddress.add(item);
                }
            }

            if (!mListAddress.isEmpty()) {
                mViewModel.onShow(mListAddress);
            } else {
                mViewModel.showMessage("Không có kết quả nào !");
            }
        } else if (type == 2) {
            List<Products> mListAddress = new ArrayList<>();

            for (Products item : mListProduct) {
                if (item.getProduct().getInformation().getPrice() < Integer.valueOf(key)) {
                    mListAddress.add(item);
                }
            }

            if (!mListAddress.isEmpty()) {
                mViewModel.onShow(mListAddress);
            } else {
                mViewModel.showMessage("Không có kết quả nào !");
            }
        }


    }

    @Override
    public void onSearch() {

        if(mBinding.edtSearch.getText().toString().isEmpty()){
            getProduct();
        }else {
            onSearchProduct(type, mBinding.edtSearch.getText().toString());
        }
    }

    @Override
    public void onDataCallBackMap(String tag) {
        Log.d("ChecMap", "OK");
        List<Products> mListChoose = new ArrayList<>();
        for (Products products : mListProduct) {
            if (products.getId().equals(tag)) {
                mListChoose.add(products);
            }
        }
        mViewModel.onShowResult(mListChoose, 0);
    }

    @Override
    public void onResultAdapter(String tag) {
        List<Products> mListChoose = new ArrayList<>();
        for (Products products : mListProduct) {
            if (products.getId().equals(tag)) {
                mListChoose.add(products);
            }
        }

        mViewModel.onShowResult(mListChoose, 1);
    }

    public void onSelectTypeFilter() {
        mViewModel.onSelectTypeFilter();
    }

    @Override
    public void filterAddress() {
        type = 1;
        hint.set("Nhập địa chỉ tìm kiếm");
    }

    @Override
    public void filterPrice() {
        type = 2;
        hint.set("Nhập số tiền lớn nhất");
    }

    @Override
    public void filterAdvance() {
        mViewModel.filterAdvance(new Gson().toJson(mListProduct));
    }
}
