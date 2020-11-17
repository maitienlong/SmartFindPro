package com.poly.smartfindpro.ui;

import com.poly.smartfindpro.ui.post.inforPost.InforPostContract;

public class MainPresenter {
    private MainContract.ViewModel mViewModel;

    public MainPresenter(MainContract.ViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    public void handleData(String mCategory, String mAmountPeople, String mPrice, String mDeposit,
                           String mGender, String mElectricityBill, String mWaterBill, String mDescription) {
        if (mCategory== "") {
            mViewModel.onErrorCategory();
        } else if (mGender.isEmpty()) {
            mViewModel.onErrorGender();
        } else if (mAmountPeople.isEmpty()|| mPrice.isEmpty() ||
                mDeposit.isEmpty() || mElectricityBill.isEmpty() ||
                mWaterBill.isEmpty()) {
            mViewModel.onErrorInfor();
        } else {
            mViewModel.onNextFragment();
        }
    }

}
