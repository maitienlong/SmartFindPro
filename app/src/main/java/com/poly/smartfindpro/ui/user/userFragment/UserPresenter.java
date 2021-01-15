package com.poly.smartfindpro.ui.user.userFragment;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.login.logout.LogoutRequest;
import com.poly.smartfindpro.data.model.product.deleteProduct.req.res.DeleteProductResponse;
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

    public ObservableField<String> dangXuat;

    public UserPresenter(Context context, UserContact.ViewModel mViewModel, FragmentUserBinding mBinding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;

        initData();
    }

    private void initData() {
        nameInfor = new ObservableField<>();
        dangXuat = new ObservableField<>();
        if (Config.isClick()) {
            getInfor();
        }

    }

    public void setTextLogOut(String msg) {
        dangXuat.set(msg);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void onClickProfile() {
        if (Config.isClick()) {
            mViewModel.onClickProfile();
        } else {
            mViewModel.showMessage(context.getString(R.string.pl_login));
        }

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
        LogoutRequest request = new LogoutRequest();
        request.setUserId(Config.TOKEN_USER);
        request.setDeviceId(Config.TOKEN_DEVICE);

        MyRetrofitSmartFind.getInstanceSmartFind().logOut(request).enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getResponseHeader().getResCode() == 200 && response.body().getResponseBody().getStatus().equalsIgnoreCase("Success")) {
                        mViewModel.onClickLogOut();
                    } else {
                        mViewModel.showMessage("Đăng xuất không thành công, vui lòng kiểm tra lại kết nối");
                    }
                } else {
                    mViewModel.showMessage("Đăng xuất không thành công, vui lòng kiểm tra lại kết nối");
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                mViewModel.showMessage("Đăng xuất không thành công, vui lòng kiểm tra lại kết nối");
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
        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + mProfile.getResponseBody().getUser().getAvatar())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgplaceholder)
                .into(mBinding.imgAvatarUser);
    }
}
