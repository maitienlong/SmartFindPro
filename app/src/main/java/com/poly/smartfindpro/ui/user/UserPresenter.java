package com.poly.smartfindpro.ui.user;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserPresenter implements UserContract.Presenter {

    private Context mContext;
    private UserContract.ViewModel mViewModel;

    public ObservableField<String> title;


    public UserPresenter(Context context, UserContract.ViewModel viewModel) {
        mContext = context;
        mViewModel = viewModel;

    }



    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

}
