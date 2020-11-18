package com.poly.smartfindpro.ui.post.inforPost;

import android.content.Context;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.ui.post.model.PostRequest;

public class InforPostPresenter{

    private Context context;

    private InforPostContract.ViewModel mViewModel;

    private PostRequest postRequest;

    public InforPostPresenter(Context context, InforPostContract.ViewModel mViewModel) {
        this.context = context;
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

    public void onSubmit(){
        postRequest = new PostRequest();


    }
}
