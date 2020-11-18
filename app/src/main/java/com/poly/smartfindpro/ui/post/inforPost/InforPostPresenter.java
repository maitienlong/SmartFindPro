package com.poly.smartfindpro.ui.post.inforPost;

import com.poly.smartfindpro.R;

public class InforPostPresenter{

    private InforPostContract.ViewModel mViewModel;

    public InforPostPresenter(InforPostContract.ViewModel mViewModel) {
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
