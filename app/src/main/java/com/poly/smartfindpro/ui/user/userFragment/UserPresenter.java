package com.poly.smartfindpro.ui.user.userFragment;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentUserBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPresenter implements UserContact.Presenter {
    private Context context;
    private UserContact.ViewModel mViewModel;
    private ProfileResponse mProfile;
    private FragmentUserBinding mBinding;

    public ObservableField<String> nameInfor;
    public UserPresenter(Context context, UserContact.ViewModel mViewModel,FragmentUserBinding mBinding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;

        initData();
    }
    private void initData() {
        nameInfor = new ObservableField<>();
        getInfor();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void onClickProfile() {
        mViewModel.onClickProfile();
    }

    @Override
    public void onClickSetting() {
        mViewModel.onClickSetting();
    }

    @Override
    public void onClickRules() {
        mViewModel.onClickRules();
    }

    @Override
    public void onClickHelp() {
        mViewModel.onClickHelp();
    }

    @Override
    public void onClickLogOut() {
        mViewModel.onClickLogOut();
    }
    public void getInfor() {
        ProfileRequest request = new ProfileRequest();
        request.setId(Config.TOKEN_USER);
        MyRetrofitSmartFind.getInstanceSmartFind().getProfile(request).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.code() == 200) {
                    mProfile = response.body();
//                    Log.d("TAG",response.body().getResponseBody().getUser().getUserName());
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
        nameInfor.set(mProfile.getResponseBody().getUser().getFullName());
        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + mProfile.getResponseBody().getUser().getAvatar())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgplaceholder)
                .into(mBinding.imgAvatarUser);
    }
}
