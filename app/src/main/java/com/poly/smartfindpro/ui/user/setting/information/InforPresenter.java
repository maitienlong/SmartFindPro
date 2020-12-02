package com.poly.smartfindpro.ui.user.setting.information;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.ui.user.setting.SettingContact;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InforPresenter implements InforContact.Presenter {

    private Context context;

    private InforContact.ViewModel mViewModel;

    private ProfileResponse mProfile;

    public ObservableField<String> nameInfor;
    public ObservableField<String> phone;
    public ObservableField<String> addressInfor;
    public ObservableField<String> idCard;

    public InforPresenter(Context context, InforContact.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
        initData();
    }

    private void initData() {
        nameInfor = new ObservableField<>();
        phone = new ObservableField<>();
        addressInfor = new ObservableField<>();
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

    private void showData(ProfileResponse mProfile) {
        nameInfor.set(mProfile.getResponse().getUser().getUserName());
        addressInfor.set(mProfile.getResponse().getUser().getAddress().getDetailAddress() + ", " + mProfile.getResponse().getUser().getAddress().getCommuneWardTown() + ", " + ", " + mProfile.getResponse().getUser().getAddress().getDistrictsTowns() + ", " + mProfile.getResponse().getUser().getAddress().getProvinceCity());
        idCard.set(mProfile.getResponse().getUser().getIdentityCard().getCode());
        phone.set(mProfile.getResponse().getUser().getPhoneNumber());
    }
}
