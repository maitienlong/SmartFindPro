package com.poly.smartfindpro.ui.detailpost;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.data.model.product.res.Products;

public class DetailPostPresenter implements DetailPostContact.Presenter {

    private Context context;
    private DetailPostContact.ViewModel mViewModel;

    public ObservableField<String> priceDetail;
    public ObservableField<String> addressDetail;
    public ObservableField<String> genderDetail;
    public ObservableField<String> amountDetail;
    public ObservableField<String> priceElectricDetail;
    public ObservableField<String> priceWaterDetail;
    public ObservableField<String> utilitiesDetail;
    public ObservableField<String> contentDetail;
    public ObservableField<String> userNameDetail;
    public ObservableField<String> timePostDetail;
    public ObservableField<String> imgAvataDetail;


    public DetailPostPresenter(Context context, DetailPostContact.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
        initData();
    }

    private void initData() {
        priceDetail = new ObservableField<>();
        addressDetail = new ObservableField<>();
        genderDetail = new ObservableField<>();
        amountDetail = new ObservableField<>();
        priceElectricDetail = new ObservableField<>();
        priceWaterDetail = new ObservableField<>();
        utilitiesDetail = new ObservableField<>();
        contentDetail = new ObservableField<>();
        userNameDetail = new ObservableField<>();
        timePostDetail = new ObservableField<>();
        imgAvataDetail = new ObservableField<>();
        getProduct();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    private void getProduct() {

    }

    public void setData(Products product) {
        priceDetail.set(product.getProduct().getInformation().getPrice().toString());
        addressDetail.set(product.getAddress().getDetailAddress() + "," + product.getAddress().getCommuneWardTown() + "," + product.getAddress().getDistrictsTowns() + "," + product.getAddress().getProvinceCity());
        genderDetail.set(product.getProduct().getInformation().getGender());
        amountDetail.set(product.getProduct().getInformation().getAmountPeople().toString());
        priceElectricDetail.set(product.getProduct().getInformation().getElectricBill().toString());
        priceWaterDetail.set(product.getProduct().getInformation().getWaterBill().toString());

        String tIch = "";
        for (String item : product.getProduct().getUtilities()) {
            if (tIch.isEmpty()) {
                tIch = tIch + item;
            } else {
                tIch = tIch + ", " + item;
            }

        }
        utilitiesDetail.set(tIch);
        contentDetail.set(product.getContent());
        userNameDetail.set(product.getUser().getUserName());
        timePostDetail.set(product.getCreateAt());
        imgAvataDetail.set(product.getUser().getAvatar());
    }
}
