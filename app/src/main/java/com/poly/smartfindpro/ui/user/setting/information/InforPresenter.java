package com.poly.smartfindpro.ui.user.setting.information;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;

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
        nameInfor.set(mProfile.getResponseBody().getUser().getPhoneNumber());
        addressInfor.set(mProfile.getResponseBody().getUser().getAddress().getDetailAddress() + ", " + mProfile.getResponseBody().getUser().getAddress().getCommuneWardTown() + ", " + ", " + mProfile.getResponseBody().getUser().getAddress().getDistrictsTowns() + ", " + mProfile.getResponseBody().getUser().getAddress().getProvinceCity());
   //     idCard.set(mProfile.getResponseBody().getIdentityCard().getCode());
        phone.set(mProfile.getResponseBody().getUser().getPhoneNumber());
    }
}
