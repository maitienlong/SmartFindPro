package com.poly.smartfindpro.ui.user.profile;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.data.model.product.req.ProductRequest;
import com.poly.smartfindpro.data.model.product.res.ProductResponse;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContact.Presenter {

    private Context context;
    private ProfileContact.ViewModel mViewModel;
    private ProfileResponse mProfile;


    public ObservableField<String> nameInfor;

    public ProfilePresenter(Context context, ProfileContact.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
        initData();
    }


    private void initData() {
        nameInfor = new ObservableField<>();

        getInfor();
        getProduct();
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void onBackClick() {
        mViewModel.onBackClick();
    }

    @Override
    public void onClickEditUser() {
        mViewModel.onClickEditUser();
    }

    public void getInfor() {
        ProfileRequest request = new ProfileRequest();
        request.setId("5fb2073ff69b03b8f8875059");
        MyRetrofitSmartFind.getInstanceSmartFind().getProfile(request).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.code() == 200) {
                    mProfile = response.body();
                    Log.d("checkResponse", response.body().getMessage());
                    showData(mProfile);

                } else {

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }
    public void getProduct() {
        ProductRequest request = new ProductRequest();
        request.setId("5fb2073ff69b03b8f8875059");
        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.code() == 200) {
                    Log.d("checkResponse", response.body().getMessage());
                    mViewModel.onShow(response.body().getResponse().getProducts());
                } else {

                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }
    private void showData(ProfileResponse mProfile) {
        nameInfor.set(mProfile.getResponse().getUser().getUserName());

    }

}
