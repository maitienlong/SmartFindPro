package com.poly.smartfindpro.ui.login.forgotPassword;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentCreatePasswordBinding;
import com.poly.smartfindpro.databinding.FragmentForgotPasswordBinding;
import com.poly.smartfindpro.ui.login.forgotPassword.recreatePassword.ReCreatePasswordFragment;
import com.poly.smartfindpro.ui.login.otp.ConfirmOTPFragment;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordFragment extends BaseDataBindFragment<FragmentForgotPasswordBinding, ForgotPasswordPresenter> implements ForgotPasswordContract.ViewModel {

    private FirebaseAuth mAuth;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forgot_password;
    }

    @Override
    protected void initView() {

        mBinding.cmtb.setTitle("Quên Mật Khẩu");
    }

    @Override
    protected void initData() {

        mPresenter = new ForgotPasswordPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);

        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("vi");
    }

    @Override
    public void OnBackClick() {
        mActivity.finish();
    }

    @Override
    public void onNextCreatePassword(String phoneNumber) {
        showLoadingDialog();

        PhoneAuthOptions phoneAuthOptions =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + phoneNumber)       // Phone number to verify
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
                                FirebaseAuth.getInstance().getFirebaseAuthSettings().forceRecaptchaFlowForTesting(true);
                                showMessage("Xác thực bằng mã OTP không thành công, vui lòng thử lại "+ e.toString());
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                hideLoading();
                                Bundle bundle = new Bundle();
                                bundle.putString(Config.POST_BUNDEL_RES, mBinding.edtPhoneNummber.getText().toString().trim());
                                getBaseActivity().goToFragment(R.id.fl_forgot_password, new ReCreatePasswordFragment(), bundle);

                            }

                            @Override
                            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                                super.onCodeAutoRetrievalTimeOut(s);
                                hideLoading();
                                showMessage("Hệ thống OTP đang mất kết nối");
                            }
                        })
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);

    }
}
