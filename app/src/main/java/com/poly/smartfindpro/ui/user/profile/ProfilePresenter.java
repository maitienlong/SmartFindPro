package com.poly.smartfindpro.ui.user.profile;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.req.ProductRequest;
import com.poly.smartfindpro.data.model.product.res.ProductResponse;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContact.Presenter {

    private Context context;
    private ProfileContact.ViewModel mViewModel;
    private ProfileResponse mProfile;
    private FragmentProfileBinding mBinding;
    private List<Products> productsList;


    public ObservableField<String> nameInfor;

    public ProfilePresenter(Context context, ProfileContact.ViewModel mViewModel, FragmentProfileBinding mBinding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
        initData();
    }


    private void initData() {
        nameInfor = new ObservableField<>();
        getInfor();
//        getProduct();
        getProductApproved();
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

    @Override
    public void onClickPending() {
        mViewModel.onClickPending();
        getProductPending();
    }

    @Override
    public void onClickApproved() {
        mViewModel.onClickApproved();
        getProductApproved();
    }

    public void getInfor() {
        ProfileRequest request = new ProfileRequest();
        request.setId(Config.TOKEN_USER);
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

    private void getProductApproved(){
        ProductRequest request = new ProductRequest();
        request.setId(Config.TOKEN_USER);

        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.code() == 200) {
//                    if (response.body().getResponseBody().getProducts())
//                    mViewModel.onShow(response.body().getResponseBody().getProducts());
                    productsList = new ArrayList<>();
                    for (int i=0; i< response.body().getResponseBody().getProducts().size(); i++){
                        if (response.body().getResponseBody().getProducts().get(i).getStatus().equals("1")){
                            productsList.add(response.body().getResponseBody().getProducts().get(i));
                        }
                    }
                    Log.d("list111", "onResponse: " + productsList.size());
                    mViewModel.onShow(productsList);

                } else {

                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }

    private void getProductPending(){ProductRequest request = new ProductRequest();
        request.setId(Config.TOKEN_USER);

        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.code() == 200) {
//                    if (response.body().getResponseBody().getProducts())
//                    mViewModel.onShow(response.body().getResponseBody().getProducts());
                    productsList = new ArrayList<>();
                    for (int i=0; i< response.body().getResponseBody().getProducts().size(); i++){
                        if (!response.body().getResponseBody().getProducts().get(i).getStatus().equals("1")){
                            productsList.add(response.body().getResponseBody().getProducts().get(i));
                        }
                    }
                    Log.d("list111", "onResponse: " + productsList.size());
                    mViewModel.onShow(productsList);

                } else {

                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });

    }


    private void getProduct() {
        ProductRequest request = new ProductRequest();
        request.setId(Config.TOKEN_USER);

        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.code() == 200) {
                    mViewModel.onShow(response.body().getResponseBody().getProducts());

                } else {

                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }

    private void showData(ProfileResponse mProfile) {
        nameInfor.set(mProfile.getResponseBody().getUser().getFullName());

        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + mProfile.getResponseBody().getUser().getAvatar())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgplaceholder)
                .into(mBinding.imgAvatarProfile);
        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + mProfile.getResponseBody().getUser().getCoverImage())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgplaceholder)
                .into(mBinding.imgCoverImage);

    }


}
