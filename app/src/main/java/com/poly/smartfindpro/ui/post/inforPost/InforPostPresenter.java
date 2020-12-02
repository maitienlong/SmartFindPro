package com.poly.smartfindpro.ui.post.inforPost;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.ui.post.model.Information;
import com.poly.smartfindpro.ui.post.model.PostRequest;

import java.io.File;

public class InforPostPresenter implements InforPostContract.Presenter {

    private Context context;

    private InforPostContract.ViewModel mViewModel;


    public InforPostPresenter(Context context, InforPostContract.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
    }

    public void handleData(String mCategory, String mAmountPeople, String mPrice, String mDeposit,
                           String mGender, String mElectricityBill, String mWaterBill, String mDescription) {
        if (mCategory.isEmpty()) {
            mViewModel.onErrorCategory();
        } else if (mGender.isEmpty()) {
            mViewModel.onErrorGender();
        } else if (mAmountPeople.isEmpty() || mPrice.isEmpty() ||
                mDeposit.isEmpty() || mElectricityBill.isEmpty() ||
                mWaterBill.isEmpty()) {
            mViewModel.onErrorInfor();
        } else {
            Information information = new Information();
            mViewModel.onNextFragment();
        }
    }


    @Override
    public void onDemoUri(String uri) {

        File file = new File(uri);
        String fileThat = file.getAbsolutePath();
        if (fileThat != null) {
            Log.d("DuMaNoa", fileThat);
        } else {
            Log.d("DuMaNob", "NONO");
        }

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }


    public void openChoosePhoto(){
        mViewModel.onShowPhoto();
    }
}
