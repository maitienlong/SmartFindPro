package com.poly.smartfindpro.ui.login.forgotPassword;

import android.view.View;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentCreatePasswordBinding;
import com.poly.smartfindpro.databinding.FragmentForgotPasswordBinding;
import com.poly.smartfindpro.ui.login.forgotPassword.recreatePassword.ReCreatePasswordFragment;

public class ForgotPasswordFragment extends BaseDataBindFragment<FragmentForgotPasswordBinding, ForgotPasswordPresenter> implements ForgotPasswordContract.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forgot_password;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mBinding.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().goToFragment(R.id.fl_forgot_password,new ReCreatePasswordFragment(), null);
            }
        });
    }
}
