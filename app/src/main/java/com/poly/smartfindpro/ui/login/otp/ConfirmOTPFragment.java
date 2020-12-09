package com.poly.smartfindpro.ui.login.otp;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentConfirmOtpBinding;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.login.createPassword.CreatePasswordFragment;
import com.poly.smartfindpro.ui.login.registerFragment.RegisterFragment;

import java.util.concurrent.TimeUnit;

public class ConfirmOTPFragment extends BaseDataBindFragment<FragmentConfirmOtpBinding, ConfirmOTPPresenter> implements ConfirmOTPContract.ViewModel {

    String verifyCode;
    String phoneNumber;

    public ConfirmOTPFragment(String verifyCode, String phoneNumber) {
        this.verifyCode = verifyCode;
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_otp;
    }

    @Override
    protected void initView() {

        mBinding.btnAction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().goToFragmentReplace(R.id.fl_Login, new CreatePasswordFragment(), null);

            }
        });

    }

    @Override
    protected void initData() {
        mBinding.btnAction2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
