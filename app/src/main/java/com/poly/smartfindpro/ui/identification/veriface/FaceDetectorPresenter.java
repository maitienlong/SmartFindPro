package com.poly.smartfindpro.ui.identification.veriface;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.ui.identification.veriface.internal.TVSelfieImage;

import java.util.ArrayList;

public class FaceDetectorPresenter implements FaceDetectorContract.Presenter {


    private FaceDetectorContract.ViewModel mViewModel;
    private Context mContext;
    private ArrayList<TVSelfieImage> mSelfieImages = new ArrayList<>();

    public ObservableField<String> accountName;

    public ObservableField<String> title;

    public FaceDetectorPresenter(Context mContext, FaceDetectorContract.ViewModel mViewModel) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
        initData();
    }

    private void initData() {
        accountName = new ObservableField<>();
        title = new ObservableField<>("Nhận diện khuôn mặt");
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }


    public void setmSelfieImages(ArrayList<TVSelfieImage> mSelfieImages) {
        this.mSelfieImages = mSelfieImages;
    }

    public void onBackClick() {
        mViewModel.onBackClick();
    }

}
