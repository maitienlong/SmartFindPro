package com.poly.smartfindpro.ui.login.otp;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentConfirmOtpBinding;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.login.createPassword.CreatePasswordFragment;

import java.util.concurrent.TimeUnit;

public class ConfirmOTPFragment extends BaseDataBindFragment<FragmentConfirmOtpBinding, ConfirmOTPPresenter> implements ConfirmOTPContract.ViewModel {
    private String verificationId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_otp;
    }

    @Override
    protected void initView() {

        mBinding.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().goToFragmentReplace(R.id.fl_Login, new CreatePasswordFragment(), null);

            }

        });
    }

    @Override
    protected void initData() {

    }
}
