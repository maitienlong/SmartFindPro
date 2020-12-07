package com.poly.smartfindpro.ui.user.profile;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.product.req.ProductRequest;
import com.poly.smartfindpro.data.model.product.res.ProductResponse;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentProfileBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContact.Presenter {

    private Context context;
    private ProfileContact.ViewModel mViewModel;
    private ProfileResponse mProfile;
    private FragmentProfileBinding mBinding;


    public ObservableField<String> nameInfor;

    public ProfilePresenter(Context context, ProfileContact.ViewModel mViewModel,FragmentProfileBinding mBinding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
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
                    showData(mProfile);

                } else {

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    private void getProduct() {
        ProductRequest request = new ProductRequest();
        request.setId("5fb2073ff69b03b8f8875059");

        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.code() == 200) {
                    mViewModel.onShow(response.body().getResponseBody().getProducts());

                }else {

                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }

    private void showData(ProfileResponse mProfile) {
        nameInfor.set(mProfile.getResponseBody().getUser().getUserName());
        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + mProfile.getResponseBody().getUser().getAvatar())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgerror)
                .into(mBinding.imgAvatarProfile);
        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + mProfile.getResponseBody().getUser().getCoverImage())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgerror)
                .into(mBinding.imgCoverImage);

    }


}
