package com.poly.smartfindpro.ui.login.registerFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.databinding.FragmentRegisterBinding;
import com.poly.smartfindpro.ui.MainActivity;
import com.poly.smartfindpro.ui.login.loginFragment.LoginFragmentPresenter;
import com.poly.smartfindpro.ui.login.otp.ConfirmOTPFragment;

public class RegisterFragment extends BaseDataBindFragment<FragmentRegisterBinding, RegisterPresenter> implements RegisterContract.ViewModel {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {
        mPresenter = new RegisterPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);


    }

    @Override
    protected void initData() {

    }


    @Override
    public void checkNumber(String jsonData) {

        Bundle bundle = new Bundle();
        bundle.putString(Config.POST_BUNDEL_RES, jsonData);
        getBaseActivity().goToFragmentReplace(R.id.fl_Login, new ConfirmOTPFragment(), bundle);

    }

}
