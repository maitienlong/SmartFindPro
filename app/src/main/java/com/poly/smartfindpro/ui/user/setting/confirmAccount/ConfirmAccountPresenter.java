package com.poly.smartfindpro.ui.user.setting.confirmAccount;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.ui.user.setting.changepass.ChangePassContact;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmAccountPresenter implements ConfirmAccountContact.Presenter {
    private Context context;
    private ConfirmAccountContact.ViewModel mViewModel;

    public ObservableField<String> titleButton;
    public ConfirmAccountPresenter(Context context, ConfirmAccountContact.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
        initData();
    }

    private void initData(){
        titleButton = new ObservableField<>();
    }

    public void setTitleButton(String title){
        titleButton.set(title);
    }

    @Override
    public void onBackClick() {
        mViewModel.onBackClick();
    }

    @Override
    public void onConfirm() {
        mViewModel.onConfirm();
    }

    public void setType() {

    }

    public void getInfor() {
        ProfileRequest request = new ProfileRequest();
        request.setId(Config.TOKEN_USER);
        MyRetrofitSmartFind.getInstanceSmartFind().getProfile(request).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.code() == 200) {
                    if(response.body().getResponseHeader().getResCode() == 200){
                        mViewModel.onSaveLevel(response.body().getResponseBody().getUser().getPhoneNumber(),
                                response.body().getResponseBody().getUser().getPassword(),
                                response.body().getResponseBody().getUser().getId(),
                                response.body().getResponseBody().getUser().getLevel(),
                                Config.TOKEN_DEVICE);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }
}
