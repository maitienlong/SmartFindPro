package com.poly.smartfindpro.ui.user.setting.changepass;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;

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
    private void showData(ProfileResponse mProfile) {
        passInfor.set(mProfile.getResponseBody().getUser().getPassword());

    }
}
