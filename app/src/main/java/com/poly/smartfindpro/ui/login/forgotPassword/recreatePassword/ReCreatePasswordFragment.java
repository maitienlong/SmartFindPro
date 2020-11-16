package com.poly.smartfindpro.ui.login.forgotPassword.recreatePassword;

import android.view.View;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentForgotPasswordBinding;
import com.poly.smartfindpro.databinding.FragmentRecreatePasswordBinding;

public class ReCreatePasswordFragment extends BaseDataBindFragment<FragmentRecreatePasswordBinding, ReCreatePasswordPresenter> implements ReCreatePasswordContract.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recreate_password;
    }

    @Override
    protected void initView() {
        mBinding.cmtb.setTitle("Đặt lại mật khẩu");
    }

    @Override
    protected void initData() {
        mPresenter = new ReCreatePasswordPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);

        mBinding.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.edtRepassword.setError(getString(R.string.error_edt_dont_match));
            }
        });

    }

    @Override
    public void OnBackClick() {
        getBaseActivity().onBackFragment();
    }
}
