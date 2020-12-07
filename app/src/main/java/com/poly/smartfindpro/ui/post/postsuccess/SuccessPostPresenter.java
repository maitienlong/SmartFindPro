package com.poly.smartfindpro.ui.post.postsuccess;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.data.model.area.Location;
import com.poly.smartfindpro.data.model.area.req.AreaReqHeader;
import com.poly.smartfindpro.data.model.area.req.AreaRequest;
import com.poly.smartfindpro.data.model.area.res.AreaResponse;
import com.poly.smartfindpro.data.model.area.result.ResultArea;
import com.poly.smartfindpro.data.model.post.req.Address;
import com.poly.smartfindpro.data.retrofit.MyRetrofit;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuccessPostPresenter implements SuccessPostContract.Presenter {
    private Context context;

    private SuccessPostContract.ViewModel mViewModel;


    public SuccessPostPresenter(Context context, SuccessPostContract.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
        initData();
    }

    private void initData() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void onConfirm() {
        mViewModel.onConfirm();
    }

    @Override
    public void onNext() {

    }
}
