package com.poly.smartfindpro.ui.detailpost;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.ActivityInformationPostBinding;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailPostPresenter implements DetailPostContact.Presenter {

    private Context context;
    private DetailPostContact.ViewModel mViewModel;

    private ActivityInformationPostBinding mBinding;

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
    public ObservableField<String> priceDepositDetail;
    public ObservableField<String> categoryDetail;
    public ObservableField<String> phoneNumberDetail;



    public DetailPostPresenter(Context context, DetailPostContact.ViewModel mViewModel, ActivityInformationPostBinding binding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = binding;
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
        priceDepositDetail = new ObservableField<>();
        categoryDetail = new ObservableField<>();
        phoneNumberDetail = new ObservableField<>();
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
        priceDetail.set(NumberFormat.getNumberInstance().format(product.getProduct().getInformation().getPrice()) +" "+ product.getProduct().getInformation().getUnit());
        addressDetail.set(product.getAddress().getDetailAddress() + "," + product.getAddress().getCommuneWardTown() + "," + product.getAddress().getDistrictsTowns() + "," + product.getAddress().getProvinceCity());
        genderDetail.set(product.getProduct().getInformation().getGender());
        amountDetail.set(product.getProduct().getInformation().getAmountPeople().toString());
        priceElectricDetail.set(NumberFormat.getNumberInstance().format(product.getProduct().getInformation().getElectricBill()) + "đ/" + product.getProduct().getInformation().getElectricUnit());
        priceWaterDetail.set(NumberFormat.getNumberInstance().format(product.getProduct().getInformation().getWaterBill()) + "đ/" + product.getProduct().getInformation().getWaterUnit());
        priceDepositDetail.set(NumberFormat.getNumberInstance().format(product.getProduct().getInformation().getDeposit()) +" "+ product.getProduct().getInformation().getUnit());
        categoryDetail.set(product.getProduct().getCategory()+"  ");
        phoneNumberDetail.set(product.getUser().getPhoneNumber());

        mBinding.cmtb.setTitle("Bài viết");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + product.getUser().getAvatar())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgplaceholder)
                .into(mBinding.imgAvatar);

        try {
            Date date = dateFormatter.parse(product.getCreateAt());

            timePostDetail.set(getTime(date));

        } catch (Exception e) {

        }
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
        userNameDetail.set(product.getUser().getPhoneNumber());

    }

    private String getTime(Date datePost) {
        String dateOK = "";
        Date today = Calendar.getInstance().getTime();
        if (!getTime("yyyy", today).equals(getTime("yyyy", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("yyyy", today)) - Integer.valueOf(getTime("yyyy", datePost))) + " năm";
        } else if (!getTime("MM", today).equals(getTime("MM", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("MM", today)) - Integer.valueOf(getTime("MM", datePost))) + " tháng";
        } else if (!getTime("dd", today).equals(getTime("dd", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("dd", today)) - Integer.valueOf(getTime("dd", datePost))) + " ngày";
        } else if (!getTime("HH", today).equals(getTime("HH", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("HH", today)) - Integer.valueOf(getTime("HH", datePost))) + " giờ";
        } else if (!getTime("mm", today).equals(getTime("mm", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("mm", today)) - Integer.valueOf(getTime("mm", datePost))) + " phút";
        } else if (!getTime("ss", today).equals(getTime("ss", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("ss", today)) - Integer.valueOf(getTime("ss", datePost))) + " giây";
        }
        return dateOK;
    }

    private String getTime(String type, Date date) {
        DateFormat dateFormat = new SimpleDateFormat(type);
        return dateFormat.format(date);
    }


    @Override
    public void onBackClick() {
        mViewModel.onBackClick();
    }

    @Override
    public void onClickCall() {
        mViewModel.onClickCall();
    }

    @Override
    public void onClickInbox() {
        mViewModel.onClickInbox();
    }

    @Override
    public void onClickProfile() {
        mViewModel.onClickProfile();
    }
}
