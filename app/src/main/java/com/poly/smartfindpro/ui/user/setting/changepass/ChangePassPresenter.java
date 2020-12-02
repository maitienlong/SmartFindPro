package com.poly.smartfindpro.ui.user.setting.changepass;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.ui.user.setting.information.InforContact;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassPresenter implements ChangePassContact.Presenter {
    private Context context;
    private ChangePassContact.ViewModel mViewModel;
    private ProfileResponse mProfile;

    public ObservableField<String> passInfor;
    public ChangePassPresenter(Context context, ChangePassContact.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
        initData();
    }
    private void initData() {
        passInfor = new ObservableField<>();

        getInfor();
    }
    @Override
    public void onBackClick() {
        mViewModel.onBackClick();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

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
        passInfor.set(mProfile.getResponse().getUser().getPassword());

    }
}
