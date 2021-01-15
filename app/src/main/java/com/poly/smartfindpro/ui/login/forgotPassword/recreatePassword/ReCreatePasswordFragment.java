package com.poly.smartfindpro.ui.login.forgotPassword.recreatePassword;

import android.view.View;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentForgotPasswordBinding;
import com.poly.smartfindpro.databinding.FragmentRecreatePasswordBinding;

public class ReCreatePasswordFragment extends BaseDataBindFragment<FragmentRecreatePasswordBinding, ReCreatePasswordPresenter> implements ReCreatePasswordContract.ViewModel {

    private String phoneNumber;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recreate_password;
    }

    @Override
    protected void initView() {
        if(getArguments() != null){
            phoneNumber = getArguments().getString(Config.POST_BUNDEL_RES);
        }
        mBinding.cmtb.setTitle("Đặt lại mật khẩu");
    }

    @Override
    protected void initData() {
        mPresenter = new ReCreatePasswordPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);

        mPresenter.setPhoneNumber(phoneNumber);
    }

    @Override
    public void OnBackClick() {
        getBaseActivity().onBackFragment();
    }

    @Override
    public void onSuccess() {
        showAlertDialog("Thông báo", "Đặt lại mật khẩu thành công", false, new AlertDialogListener() {
            @Override
            public void onAccept() {
                mActivity.finish();
            }

            @Override
            public void onCancel() {
                mActivity.finish();
            }
        });
    }
}
