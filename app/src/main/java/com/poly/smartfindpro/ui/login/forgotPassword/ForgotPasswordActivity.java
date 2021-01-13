package com.poly.smartfindpro.ui.login.forgotPassword;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityForgotPasswordBinding;
import com.poly.smartfindpro.ui.login.LoginPresenter;
import com.poly.smartfindpro.ui.login.forgotPassword.ForgotPasswordFragment;

public class ForgotPasswordActivity extends BaseDataBindActivity<ActivityForgotPasswordBinding, LoginPresenter> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void initView() {
        goToFragmentReplace(R.id.fl_forgot_password, new ForgotPasswordFragment(), null);
    }

    @Override
    protected void initData() {

    }
}