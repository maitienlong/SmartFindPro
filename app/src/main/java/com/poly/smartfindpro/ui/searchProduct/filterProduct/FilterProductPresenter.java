package com.poly.smartfindpro.ui.searchProduct.filterProduct;

import android.content.Context;

import androidx.databinding.ObservableField;

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
    public void setData(String valueFilter, int intTieuChi) {
        List<Products> mListChoose = new ArrayList<>();
        if (mListResult.size() > 0) {
            for (Products item : mListResult) {
                switch (intTieuChi) {
                    case 0:
                        if (valueFilter.isEmpty()) {
                            mListChoose.add(item);
                        } else {
                            if (item.getProduct().getCategory().equals(valueFilter)) {
                                mListChoose.add(item);
                            }
                        }

                        break;
                    case 1:
                        if (valueFilter.isEmpty()) {
                            mListChoose.add(item);
                        } else {
                            if (item.getProduct().getInformation().getAmountPeople() == Integer.valueOf(valueFilter)) {
                                mListChoose.add(item);
                            }
                        }
                        break;
                    case 3:
                        if (valueFilter.isEmpty()) {
                            mListChoose.add(item);
                        } else {
                            if (item.getProduct().getInformation().getPrice() > 1000000 && item.getProduct().getInformation().getPrice() < Integer.valueOf(valueFilter)) {
                                mListChoose.add(item);
                            }
                        }
                        break;
                    case 4:
                        if (valueFilter.isEmpty()) {
                            mListChoose.add(item);
                        } else if (valueFilter.equals("Tất cả")) {
                            mListChoose.add(item);
                        } else {
                            if (item.getProduct().getInformation().getGender().equals(valueFilter)) {
                                mListChoose.add(item);
                            }
                        }
                        break;
                    case 5:
                        if (valueFilter.isEmpty()) {
                            mListChoose.add(item);
                        } else {
                            if (item.getProduct().getInformation().getElectricBill() == Integer.valueOf(valueFilter)) {
                                mListChoose.add(item);
                            }
                        }
                        break;
                    case 6:
                        if (valueFilter.isEmpty()) {
                            mListChoose.add(item);
                        } else {
                            if (item.getProduct().getInformation().getWaterBill() == Integer.valueOf(valueFilter)) {
                                mListChoose.add(item);
                            }
                        }
                        break;
                }
            }
        } else {
            for (Products item : mListProduct) {
                switch (intTieuChi) {
                    case 0:
                        if (valueFilter.isEmpty()) {
                            mListChoose.add(item);
                        } else {
                            if (item.getProduct().getCategory().equals(valueFilter)) {
                                mListChoose.add(item);
                            }
                        }

                        break;
                    case 1:
                        if (valueFilter.isEmpty()) {
                            mListChoose.add(item);
                        } else {
                            if (item.getProduct().getInformation().getAmountPeople() == Integer.valueOf(valueFilter)) {
                                mListChoose.add(item);
                            }
                        }
                        break;
                    case 3:
                        if (valueFilter.isEmpty()) {
                            mListChoose.add(item);
                        } else {
                            if (item.getProduct().getInformation().getPrice() > 1000000 && item.getProduct().getInformation().getPrice() < Integer.valueOf(valueFilter)) {
                                mListChoose.add(item);
                            }
                        }
                        break;
                    case 4:
                        if (valueFilter.isEmpty()) {
                            mListChoose.add(item);
                        } else if (valueFilter.equals("Tất cả")) {
                            mListChoose.add(item);
                        } else {
                            if (item.getProduct().getInformation().getGender().equals(valueFilter)) {
                                mListChoose.add(item);
                            }
                        }
                        break;
                    case 5:
                        if (valueFilter.isEmpty()) {
                            mListChoose.add(item);
                        } else {
                            if (item.getProduct().getInformation().getElectricBill() == Integer.valueOf(valueFilter)) {
                                mListChoose.add(item);
                            }
                        }
                        break;
                    case 6:
                        if (valueFilter.isEmpty()) {
                            mListChoose.add(item);
                        } else {
                            if (item.getProduct().getInformation().getWaterBill() == Integer.valueOf(valueFilter)) {
                                mListChoose.add(item);
                            }
                        }
                        break;
                }
            }
        }
        mListResult.clear();
        mListResult.addAll(mListChoose);
        mViewModel.onShow(mListResult);

    }

    @Override
    public void onBackClick() {
        mViewModel.onBackClick();
    }
}
