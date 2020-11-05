package com.poly.smartfindpro.ui.login.registerFragment;

import android.view.View;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.databinding.FragmentRegisterBinding;
import com.poly.smartfindpro.ui.login.otp.ConfirmOTPFragment;

public class RegisterFragment extends BaseDataBindFragment<FragmentRegisterBinding, RegisterPresenter> implements RegisterContract.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {

        mBinding.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().goToFragmentReplace(R.id.fl_Login, new ConfirmOTPFragment(), null);
            }
        });

    }

    @Override
    protected void initData() {

    }


}
