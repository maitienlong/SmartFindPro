package com.poly.smartfindpro.ui.user.setting.information;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.base.User;
import com.poly.smartfindpro.data.model.home.res.Address;
import com.poly.smartfindpro.data.model.identification.RequestIndentifi;
import com.poly.smartfindpro.data.model.product.deleteProduct.req.res.DeleteProductResponse;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.req.UserRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.model.register.resphonenumber.CheckPhoneResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentInformationProfileBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InforPresenter implements InforContact.Presenter {

    private Context context;

    private InforContact.ViewModel mViewModel;

    private FragmentInformationProfileBinding mBinding;

    private ProfileResponse mProfile;

    public ObservableField<String> nameInfor;
    public ObservableField<String> phone;
    public ObservableField<String> gender;
    public ObservableField<String> birthday;
    public ObservableField<String> addressInfor;
    public ObservableField<String> idCard;

    public InforPresenter(Context context, InforContact.ViewModel mViewModel, FragmentInformationProfileBinding mBinding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
        initData();
    }

    private void initData() {
        nameInfor = new ObservableField<>();
        phone = new ObservableField<>();
        birthday = new ObservableField<>();
        addressInfor = new ObservableField<>();
        gender = new ObservableField<>();
        idCard = new ObservableField<>();

        getInfor();
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
    public void onCLickUpdate() {
        mViewModel.onCLickUpdate();
        updateUser();
    }

    @Override
    public void onClickAddress() {
        mViewModel.onClickAddress();
    }

    public void updateUser() {
        UserRequest request = new UserRequest();

        request.setUserId(Config.TOKEN_USER);
        request.setFullname(nameInfor.get());
        request.setBirth(birthday.get());
        request.setGender(gender.get());
        request.setAddress(mProfile.getResponseBody().getUser().getAddress());

        MyRetrofitSmartFind.getInstanceSmartFind().getUpdateUser(request).enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if (response.body().getResponseHeader().getResCode() == 200 &&
                        response.body().getResponseHeader().getResMessage().equals("Success")) {

                    Log.d("check", new Gson().toJson(response.body()));

                    Toast.makeText(context, "Successfully upgraded account level 1", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("check", new Gson().toJson(response.body()));

                    Toast.makeText(context, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {

            }
        });
    }


    public void getInfor() {
        ProfileRequest request = new ProfileRequest();
        request.setId(Config.TOKEN_USER);
        MyRetrofitSmartFind.getInstanceSmartFind().getProfile(request).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.code() == 200) {
                    mProfile = response.body();
//                    Log.d("TAG",response.body().getResponseBody().getUser().getFullName());
                    showData(mProfile);

                } else {

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    private void showData(ProfileResponse mProfile) {
        nameInfor.set(mProfile.getResponseBody().getUser().getFullname());
        addressInfor.set(mProfile.getResponseBody().getUser().getAddress().getDetailAddress() + ", " + mProfile.getResponseBody().getUser().getAddress().getCommuneWardTown() + ", " + ", " + mProfile.getResponseBody().getUser().getAddress().getDistrictsTowns() + ", " + mProfile.getResponseBody().getUser().getAddress().getProvinceCity());
        //     idCard.set(mProfile.getResponseBody().getIdentityCard().getCode());
        gender.set(mProfile.getResponseBody().getUser().getGender());
        birthday.set(mProfile.getResponseBody().getUser().getBirth());
    }
}
