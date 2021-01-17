package com.poly.smartfindpro.ui.user.setting.changepass;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.forgotpasswrd.ForgotPasswordRequest;
import com.poly.smartfindpro.data.model.product.deleteProduct.req.res.DeleteProductResponse;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentChangePassBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassPresenter implements ChangePassContact.Presenter {
    private Context context;
    private ChangePassContact.ViewModel mViewModel;
    private FragmentChangePassBinding mBinding;
    private ProfileResponse mProfile;

    public ObservableField<String> passInfor;
    public ObservableField<String> oldPass;
    public ObservableField<String> newPass;
    public ObservableField<String> confirmPass;

    public ChangePassPresenter(Context context, ChangePassContact.ViewModel mViewModel, FragmentChangePassBinding mBinding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
        initData();
    }

    private void initData() {
        passInfor = new ObservableField<>();
        oldPass = new ObservableField<>();
        newPass = new ObservableField<>();
        confirmPass = new ObservableField<>();
        getInfor();
    }

    @Override
    public void onBackClick() {
        mViewModel.onBackClick();
    }

    @Override
    public void onChangePass() {
        Log.d("check", mProfile.getResponseBody().getUser().getPassword());
        if (!mBinding.edtPassOld.getText().toString().trim().equals(mProfile.getResponseBody().getUser().getPassword())) {
            mViewModel.showMessage("Mật khẩu cũ không chính xác");
        } else {
            if (!mBinding.edtPassNew.getText().toString().trim().equals(mBinding.edtPassConfirm.getText().toString().trim())) {
                mViewModel.showMessage("Xác nhận mật khẩu không giống mật khẩu mới");
            } else {
                updatePassword();
            }

        }
    }

    public void updatePassword() {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setPhone_number(mProfile.getResponseBody().getUser().getPhoneNumber());
        request.setPassword(mBinding.edtPassNew.getText().toString().trim());
        MyRetrofitSmartFind.getInstanceSmartFind().changePassword(request).enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if (response.body().getResponseHeader().getResCode() == 200
                        && response.body().getResponseBody().getStatus().equalsIgnoreCase("Success")) {
                    mViewModel.showMessage("Đổi mật khẩu thành công");
                    mBinding.edtPassOld.setText("");
                    mBinding.edtPassNew.setText("");
                    mBinding.edtPassConfirm.setText("");
                } else {
                    mViewModel.showMessage("Đổi mật khẩu không thành công");
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                mViewModel.showMessage(context.getString(R.string.services_not_avail));
            }
        });
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
