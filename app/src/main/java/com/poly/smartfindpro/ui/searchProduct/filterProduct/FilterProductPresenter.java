package com.poly.smartfindpro.ui.searchProduct.filterProduct;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.poly.smartfindpro.data.model.home.req.HomeRequest;
import com.poly.smartfindpro.data.model.home.res.HomeResponse;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.ActivityFilterProductBinding;
import com.poly.smartfindpro.databinding.ActivitySearchProductBinding;
import com.poly.smartfindpro.ui.searchProduct.SearchProductContract;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterProductPresenter implements FilterProductContact.Presenter {
    private Context mContext;

    private FilterProductContact.ViewModel mViewModel;

    private ActivityFilterProductBinding mBinding;

    private List<Products> mListProduct;

    private List<Products> mListResult;

    public ObservableField<String> title;

    public FilterProductPresenter(Context mContext, FilterProductContact.ViewModel mViewModel, ActivityFilterProductBinding mBinding) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
        initData();
    }

    private void initData() {
        mListProduct = new ArrayList<>();
        mListResult = new ArrayList<>();
        title = new ObservableField<>("Tìm kiếm nâng cao");
    }

    public void setProducts(List<Products> products) {
        this.mListProduct = products;

    }

    @Override
    public void onClickFilter() {
        mViewModel.onClickFilter();
    }

    @Override
    public void onBackClick() {
        mViewModel.onBackClick();
    }

    @Override
    public void onClickFilter(List<Priority> productList, List<Products> mListProduct) {
        mListResult.clear();
        for (int i = 0; i < productList.size(); i++) {
            if (i == 0) {
                if (productList.get(i).getId() == 0) {
                    mListResult.addAll(onFilterTheLoai(mListProduct, productList.get(i).getValue()));
                } else if (productList.get(i).getId() == 1) {
                    mListResult.addAll(onFilterGiaTien(mListProduct, Integer.parseInt(productList.get(i).getValue())));
                } else if (productList.get(i).getId() == 3) {
                    mListResult.addAll(onFilterGioiTinh(mListProduct, productList.get(i).getValue()));
                } else if (productList.get(i).getId() == 2) {
                    mListResult.addAll(onFilterSoLuong(mListProduct, Integer.parseInt(productList.get(i).getValue())));
                }
            } else {
                if (productList.get(i).getId() == 0) {
                    mListResult = (onFilterTheLoai(mListResult, productList.get(i).getValue()));
                } else if (productList.get(i).getId() == 1) {
                    mListResult = (onFilterGiaTien(mListResult, Integer.parseInt(productList.get(i).getValue())));
                } else if (productList.get(i).getId() == 3) {
                    mListResult = (onFilterGioiTinh(mListResult, productList.get(i).getValue()));
                } else if (productList.get(i).getId() == 2) {
                    mListResult = (onFilterSoLuong(mListResult, Integer.parseInt(productList.get(i).getValue())));
                }
            }
        }

        if (mListResult.size() > 0) {
            mViewModel.onShow(mListResult);
        } else {
            mViewModel.onShowMsg("Không tìm thấy kết quả nào");
        }
    }


    private List<Products> onFilterTheLoai(List<Products> mListProduct, String value) {
        List<Products> listResult = new ArrayList<>();

        for (int i = 0; i < mListProduct.size(); i++) {
            if (mListProduct.get(i).getProduct().getCategory().equalsIgnoreCase(value)) {
                listResult.add(mListProduct.get(i));
            }
        }

        return listResult;
    }

    private List<Products> onFilterSoLuong(List<Products> mListProduct, int value) {
        List<Products> listResult = new ArrayList<>();

        for (int i = 0; i < mListProduct.size(); i++) {
            if (mListProduct.get(i).getProduct().getInformation().getAmountPeople() == value) {
                listResult.add(mListProduct.get(i));
            }
        }

        return listResult;
    }

    private List<Products> onFilterGiaTien(List<Products> mListProduct, int value) {
        List<Products> listResult = new ArrayList<>();

        for (int i = 0; i < mListProduct.size(); i++) {
            if (mListProduct.get(i).getProduct().getInformation().getPrice() > 500000 && mListProduct.get(i).getProduct().getInformation().getPrice() < value) {
                listResult.add(mListProduct.get(i));
            }
        }

        return listResult;
    }

    private List<Products> onFilterGioiTinh(List<Products> mListProduct, String value) {
        List<Products> listResult = new ArrayList<>();

        for (int i = 0; i < mListProduct.size(); i++) {
            if (mListProduct.get(i).getProduct().getInformation().getGender().equalsIgnoreCase(value)) {
                listResult.add(mListProduct.get(i));
            }
        }

        return listResult;
    }

}
