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
    public void onClickFilter(FilterTool filterTool, List<String> priority) {

        for (int i = 0; i < priority.size(); i++) {
            if (i == 0) {
                if (priority.get(i).equalsIgnoreCase("theloai")) {
                    mListResult.addAll(onFilterTheLoai(mListProduct, filterTool));
                } else if (priority.get(i).equalsIgnoreCase("giatien")) {
                    mListResult.addAll(onFilterGiaTien(mListProduct, filterTool));
                } else if (priority.get(i).equalsIgnoreCase("gioitinh")) {
                    mListResult.addAll(onFilterGioiTinh(mListProduct, filterTool));
                } else if (priority.get(i).equalsIgnoreCase("soluong")) {
                    mListResult.addAll(onFilterSoLuong(mListProduct, filterTool));
                } else if (priority.get(i).equalsIgnoreCase("tiendien")) {
                    mListResult.addAll(onFilterGiaTienDien(mListProduct, filterTool));
                } else if (priority.get(i).equalsIgnoreCase("tiennuoc")) {
                    mListResult.addAll(onFilterGiaTienNuoc(mListProduct, filterTool));
                }
            } else {
                if (priority.get(i).equalsIgnoreCase("theloai")) {
                    mListResult = (onFilterTheLoai(mListResult, filterTool));
                } else if (priority.get(i).equalsIgnoreCase("giatien")) {
                    mListResult = (onFilterGiaTien(mListResult, filterTool));
                } else if (priority.get(i).equalsIgnoreCase("gioitinh")) {
                    mListResult = (onFilterGioiTinh(mListResult, filterTool));
                } else if (priority.get(i).equalsIgnoreCase("soluong")) {
                    mListResult = (onFilterSoLuong(mListResult, filterTool));
                } else if (priority.get(i).equalsIgnoreCase("tiendien")) {
                    mListResult = (onFilterGiaTienDien(mListResult, filterTool));
                } else if (priority.get(i).equalsIgnoreCase("tiennuoc")) {
                    mListResult = (onFilterGiaTienNuoc(mListResult, filterTool));
                }
            }
        }

        if (mListResult.size() > 0){
            mViewModel.onShow(mListResult);
        }else {
            mViewModel.onShowMsg("Không tìm thấy kết quả nào");
        }
    }


    private List<Products> onFilterTheLoai(List<Products> mListProduct, FilterTool value) {
        List<Products> listResult = new ArrayList<>();

        for (int i = 0; i < mListProduct.size(); i++) {
            if (mListProduct.get(i).getProduct().getCategory().equalsIgnoreCase(value.getTheLoai())) {
                listResult.add(mListProduct.get(i));
            }
        }

        return listResult;
    }

    private List<Products> onFilterSoLuong(List<Products> mListProduct, FilterTool value) {
        List<Products> listResult = new ArrayList<>();

        for (int i = 0; i < mListProduct.size(); i++) {
            if (mListProduct.get(i).getProduct().getInformation().getAmountPeople() == value.getSoLuongNguoi()) {
                listResult.add(mListProduct.get(i));
            }
        }

        return listResult;
    }

    private List<Products> onFilterGiaTien(List<Products> mListProduct, FilterTool value) {
        List<Products> listResult = new ArrayList<>();

        for (int i = 0; i < mListProduct.size(); i++) {
            if (mListProduct.get(i).getProduct().getInformation().getPrice() > 500000 && mListProduct.get(i).getProduct().getInformation().getPrice() < value.getGia()) {
                listResult.add(mListProduct.get(i));
            }
        }

        return listResult;
    }

    private List<Products> onFilterGioiTinh(List<Products> mListProduct, FilterTool value) {
        List<Products> listResult = new ArrayList<>();

        for (int i = 0; i < mListProduct.size(); i++) {
            if (mListProduct.get(i).getProduct().getInformation().getGender().equalsIgnoreCase(value.getGioiTinh())) {
                listResult.add(mListProduct.get(i));
            }
        }

        return listResult;
    }

    private List<Products> onFilterGiaTienDien(List<Products> mListProduct, FilterTool value) {
        List<Products> listResult = new ArrayList<>();

        for (int i = 0; i < mListProduct.size(); i++) {
            if (mListProduct.get(i).getProduct().getInformation().getElectricBill() <= value.getTienDien()) {
                listResult.add(mListProduct.get(i));
            }
        }

        return listResult;
    }

    private List<Products> onFilterGiaTienNuoc(List<Products> mListProduct, FilterTool value) {
        List<Products> listResult = new ArrayList<>();

        for (int i = 0; i < mListProduct.size(); i++) {
            if (mListProduct.get(i).getProduct().getInformation().getWaterBill() <= value.getTienNuoc()) {
                listResult.add(mListProduct.get(i));
            }
        }

        return listResult;
    }
}
