package com.poly.smartfindpro.ui.login.otp;

import android.view.View;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentConfirmOtpBinding;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.login.createPassword.CreatePasswordFragment;

public class ConfirmOTPFragment extends BaseDataBindFragment<FragmentConfirmOtpBinding, ConfirmOTPPresenter> implements ConfirmOTPContract.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_otp;
    }

    @Override
    protected void initView() {

        mBinding.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().goToFragmentReplace(R.id.fl_Login,new CreatePasswordFragment(), null);
            }
        });

    }

    @Override
    protected void initData() {

    }
}
