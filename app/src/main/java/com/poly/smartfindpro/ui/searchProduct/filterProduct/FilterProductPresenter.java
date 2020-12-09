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

    public FilterProductPresenter(Context mContext, FilterProductContact.ViewModel mViewModel, ActivityFilterProductBinding mBinding) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
        initData();
    }

    private void initData() {
        mListProduct = new ArrayList<>();
    }

    public void setProducts(List<Products> products) {
        this.mListProduct = products;
    }

    @Override
    public void onClickFilter() {
        mViewModel.onClickFilter();
    }

    @Override
    public void setData(String theLoai, int soLuong, int giaTien, int tienDien, int tienNuoc, String gioiTinh) {
        List<Products> mListChoose = new ArrayList<>();
        for (Products item : mListProduct) {
            if (item.getProduct().getCategory().toLowerCase().contains(theLoai)) {
                if (item.getProduct().getInformation().getPrice() > 1000000 && item.getProduct().getInformation().getPrice() < giaTien) {
                    if (item.getProduct().getInformation().getGender().equals(gioiTinh)) {
                        mListChoose.add(item);
                    }
                }
            }
        }
        if (mListChoose.size() > 0) {
            mViewModel.onShow(mListChoose);
        }else {
            mViewModel.onShowMsg("Không tìm kiếm thấy kết quả nào");
        }

    }
}
