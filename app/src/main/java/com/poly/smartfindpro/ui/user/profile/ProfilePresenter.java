package com.poly.smartfindpro.ui.user.profile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.deleteProduct.req.DeleteProductRequest;
import com.poly.smartfindpro.data.model.product.deleteProduct.req.res.DeleteProductResponse;
import com.poly.smartfindpro.data.model.product.req.ProductRequest;
import com.poly.smartfindpro.data.model.product.res.ProductResponse;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.model.product.totalPeopleLease.TotalPeopleLeaseRequest;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.model.register.resphonenumber.CheckPhoneResponse;
import com.poly.smartfindpro.data.model.updateavatar.RequestUpdateAvatar;
import com.poly.smartfindpro.data.model.updateavatar.RequestUpdateCover;
import com.poly.smartfindpro.data.model.uploadphoto.ResponsePostPhoto;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentProfileBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContact.Presenter {

    private Context context;
    private ProfileContact.ViewModel mViewModel;
    private ProfileResponse mProfile;
    private DeleteProductResponse mDelete;
    private CheckPhoneResponse mGetTotal;
    private Products mProduct;
    private FragmentProfileBinding mBinding;
    private List<Products> productsList;

    public ObservableField<String> nameInfor;
    public ObservableField<Integer> heightStatus;

    public ProfilePresenter(Context context, ProfileContact.ViewModel mViewModel, FragmentProfileBinding mBinding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
        initData();
    }


    private void initData() {
        nameInfor = new ObservableField<>();
        heightStatus = new ObservableField<>(Config.HEIGHT_STATUS_BAR);
        getInfor();
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


    public void onClickImage() {
        mViewModel.onShowPhoto();
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

    @Override
    public void onGetTotalPeople() {

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

    private void getProductApproved() {
        mViewModel.showLoading();
        ProductRequest request = new ProductRequest();
        request.setId(Config.TOKEN_USER);

        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.code() == 200) {
                    productsList = new ArrayList<>();
                    if (response.body().getResponseBody() != null && response.body().getResponseBody().getProducts() != null) {
                        for (int i = 0; i < response.body().getResponseBody().getProducts().size(); i++) {
                            if (response.body().getResponseBody().getProducts().get(i).getStatus().equals("1")) {
                                productsList.add(response.body().getResponseBody().getProducts().get(i));
                            }
                        }
                        mViewModel.onShow(productsList);
                    }
                } else {
                    mViewModel.hideLoading();
                    mViewModel.showMessage(context.getString(R.string.services_not_avail));
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                mViewModel.hideLoading();
                mViewModel.showMessage(context.getString(R.string.services_not_avail));
            }
        });
    }

    private void getProductPending() {
        mViewModel.showLoading();
        ProductRequest request = new ProductRequest();
        request.setId(Config.TOKEN_USER);

        MyRetrofitSmartFind.getInstanceSmartFind().getProduct(request).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getResponseBody() != null && response.body().getResponseBody().getProducts() != null) {
                        productsList = new ArrayList<>();
                        for (int i = 0; i < response.body().getResponseBody().getProducts().size(); i++) {
                            if (!response.body().getResponseBody().getProducts().get(i).getStatus().equals("1")) {
                                productsList.add(response.body().getResponseBody().getProducts().get(i));
                            }
                        }
                        mViewModel.onShow(productsList);
                    }


                } else {
                    mViewModel.hideLoading();
                    mViewModel.showMessage(context.getString(R.string.services_not_avail));
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                mViewModel.hideLoading();
                mViewModel.showMessage(context.getString(R.string.services_not_avail));

            }
        });

    }

    @Override
    public void setAmountPeople(String amount) {

    }

    public void getTotalPeopleLease(String idPost, String amount) {
        TotalPeopleLeaseRequest request = new TotalPeopleLeaseRequest();
        request.setTotal_people_lease(amount);
        request.setUserId(Config.TOKEN_USER);
        request.setId(idPost);
        MyRetrofitSmartFind.getInstanceSmartFind().getTotalPeopleLease(request).enqueue(new Callback<CheckPhoneResponse>() {
            @Override
            public void onResponse(Call<CheckPhoneResponse> call, Response<CheckPhoneResponse> response) {
                if (response.code() == 200 && response.body().getResponseHeader().getResCode() == 200) {

//                    mGetTotal = response.body();
                    if (response.body().getResponseBody().getStatus().equalsIgnoreCase("success")) {
                        mViewModel.showMessage("Thêm thành công");
                        Log.d("checkStatus", response.body().getResponseBody().getStatus());
                    } else {
                        mViewModel.showMessage("thêm thất bại do: " + response.body().getResponseBody().getStatus());
                        Log.d("checkStatus", response.body().getResponseBody().getStatus());
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<CheckPhoneResponse> call, Throwable t) {

            }
        });
    }

    public void getDeleteProduct(String idPost) {
        DeleteProductRequest request = new DeleteProductRequest();
        request.setUserId(Config.TOKEN_USER);
        request.setId(idPost);
        MyRetrofitSmartFind.getInstanceSmartFind().getDeleteProduct(request).enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if (response.code() == 200 && response.body().getResponseHeader().getResCode() == 200) {

                    mDelete = response.body();
                    Log.d("checkDelete", mDelete.getResponseHeader().getResCode().toString());
                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    getProductPending();
                    getProductApproved();
                } else {

                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {

            }
        });
    }

    public void getUpdateProduct(String idPost, String jsonData) {
        mViewModel.onUpdate(new Gson().toJson(jsonData));
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
        nameInfor.set(mProfile.getResponseBody().getUser().getFullname());
        Log.d("checkPhone", mProfile.getResponseBody().getUser().getPhoneNumber());

        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + mProfile.getResponseBody().getUser().getAvatar())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.darkgray)
                .into(mBinding.imgAvatarProfile);
        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + mProfile.getResponseBody().getUser().getCoverImage())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.drawable.bg_gradient_gray)
                .into(mBinding.imgCoverImage);

    }

    public void onClickChangeAvatar() {
        mViewModel.onClickChangeAvatar();
    }

    public void onClickChangeCover() {
        mViewModel.onClickChangeCover();
    }

    public void initPhoto(int id, String realParh) {
        mViewModel.showLoading();
        List<String> mListPhoto = new ArrayList<>();
        mListPhoto.add(realParh);

        MultipartBody.Part[] surveyImagesParts;

        surveyImagesParts = new MultipartBody.Part[mListPhoto.size()];

        for (int i = 0; i < mListPhoto.size(); i++) {

            File file = new File(mListPhoto.get(i));

            RequestBody resBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            surveyImagesParts[i] = MultipartBody.Part.createFormData("photo", file.getAbsolutePath(), resBody);
        }

        MyRetrofitSmartFind.getInstanceSmartFind().postImageMulti(surveyImagesParts).enqueue(new Callback<ResponsePostPhoto>() {
            @Override
            public void onResponse(Call<ResponsePostPhoto> call, Response<ResponsePostPhoto> response) {
                if (response.code() == 200) {
                    mViewModel.hideLoading();
                    if (id == 0) {
                        onChangeAvatar(response.body().getResponseBody().getAddressImage().get(0));
                    } else if (id == 1) {
                        onChangeCover(response.body().getResponseBody().getAddressImage().get(0));
                    }

                } else {
                    mViewModel.showMessage(context.getString(R.string.services_not_avail) + " - " + response.code() + " - msg: Đăng ảnh không thành công");
                }
            }

            @Override
            public void onFailure(Call<ResponsePostPhoto> call, Throwable t) {
                mViewModel.hideLoading();
                Log.d("CheckUpLoadImage", t.toString());
                mViewModel.showMessage(context.getString(R.string.services_not_avail) + " - msg: Đăng ảnh không thành công");
            }
        });
    }

    private void onChangeAvatar(String urlImage) {
        RequestUpdateAvatar requestUpdateAvatar = new RequestUpdateAvatar(Config.TOKEN_USER, urlImage);
        MyRetrofitSmartFind.getInstanceSmartFind().updateAvatar(requestUpdateAvatar).enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if (response.code() == 200) {
                    mViewModel.hideLoading();
                    if (response.body().getResponseHeader().getResCode() == 200) {
                        getInfor();
                        getProductApproved();
                        Toast.makeText(context, "Ảnh đại diện đã được thay đổi", Toast.LENGTH_SHORT).show();
                    } else {
                        mViewModel.showMessage("Hiện tại không thể thay đổi ảnh đại diện");
                    }
                } else {
                    mViewModel.hideLoading();
                    mViewModel.showMessage(context.getString(R.string.services_not_avail));
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {

            }
        });
    }

    private void onChangeCover(String urlImage) {
        RequestUpdateCover requestUpdateAvatar = new RequestUpdateCover(Config.TOKEN_USER, urlImage);
        MyRetrofitSmartFind.getInstanceSmartFind().updateCover(requestUpdateAvatar).enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if (response.code() == 200) {
                    mViewModel.hideLoading();
                    if (response.body().getResponseHeader().getResCode() == 200) {
                        getInfor();
                        Toast.makeText(context, "Ảnh bìa đã được thay đổi", Toast.LENGTH_SHORT).show();
                    } else {
                        mViewModel.showMessage("Hiện tại không thể thay đổi ảnh bìa");
                    }
                } else {
                    mViewModel.hideLoading();
                    mViewModel.showMessage(context.getString(R.string.services_not_avail));
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {

            }
        });
    }
}
