package com.poly.smartfindpro.ui.login.otp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.register.regisRequest.RegisterRequest;
import com.poly.smartfindpro.databinding.FragmentConfirmOtpBinding;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.login.createPassword.CreatePasswordFragment;
import com.poly.smartfindpro.ui.login.registerFragment.RegisterPresenter;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class ConfirmOTPFragment extends BaseDataBindFragment<FragmentConfirmOtpBinding, ConfirmOTPPresenter> implements ConfirmOTPContract.ViewModel {

    private String idOTP;

    private String phone;

    private FirebaseAuth mAuth;

    private void getData() {
        if (getArguments() != null) {
            idOTP = getArguments().getString(Config.POST_BUNDEL_ID_OTP);
            phone = getArguments().getString(Config.POST_BUNDEL_ID_OTP);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_otp;
    }


    @Override
    protected void initView() {
        getData();
        mPresenter = new ConfirmOTPPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("vi");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClickConfirm() {
        if (mBinding.edtOtp.getText().toString().isEmpty()) {
            showMessage("Mã OTP không được để trống");
        }
        if (mBinding.edtOtp.getText().toString().length() != 6) {
            showMessage("Mã OTP phải là 6 ký tự");
        } else {
            onconfirmOTP();
        }
    }

    @Override
    public void onResendOTP() {
        showLoadingDialog();
        PhoneAuthOptions phoneAuthOptions =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(mActivity)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                hideLoading();
                                showMessage("Gửi mã OTP Thành công");
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                hideLoading();
                                showMessage("Xác thực bằng mã OTP không thành công, vui lòng thử lại");
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                hideLoading();
                                showMessage("Mã OTP đã được gửi lại");
                                idOTP = s;
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                              showMessage("Lỗi đăng nhập firebase: " + task.getException());
                            }
                        }
                    }
                });
    }

    private void onconfirmOTP() {
        showLoadingDialog();
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(idOTP, mBinding.edtOtp.getText().toString());
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideLoading();
                if (task.isSuccessful()) {
                  //  signInWithPhoneAuthCredential(phoneAuthCredential);
                    Bundle bundle = new Bundle();
                    bundle.putString(Config.POST_BUNDEL_RES, getArguments().getString(Config.POST_BUNDEL_RES));
                    getBaseActivity().goToFragmentReplace(R.id.fl_Login, new CreatePasswordFragment(), bundle);
                }
            }
        });
    }

}
