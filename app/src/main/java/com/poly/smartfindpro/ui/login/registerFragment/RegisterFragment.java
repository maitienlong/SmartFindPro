package com.poly.smartfindpro.ui.login.registerFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentRegisterBinding;
import com.poly.smartfindpro.ui.login.otp.ConfirmOTPFragment;

import java.util.concurrent.TimeUnit;


public class RegisterFragment extends BaseDataBindFragment<FragmentRegisterBinding, RegisterPresenter> implements RegisterContract.ViewModel {

    private FirebaseAuth mAuth;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {
        mPresenter = new RegisterPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("vi");
    }

    @Override
    protected void initData() {

    }


    @Override
    public void checkNumber(String jsonData, String phone) {
        showLoadingDialog();
        PhoneAuthOptions phoneAuthOptions =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+phone)       // Phone number to verify
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
                                showMessage("Xác thực bằng mã OTP không thành công, vui lòng thử lại ");
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                hideLoading();
                                Bundle bundle = new Bundle();
                                bundle.putString(Config.POST_BUNDEL_RES, jsonData);
                                bundle.putString(Config.POST_BUNDEL_ID_OTP, s);
                                bundle.putString(Config.POST_BUNDEL_SDT, phone);
                                getBaseActivity().goToFragmentReplace(R.id.fl_Login, new ConfirmOTPFragment(), bundle);
                            }

                            @Override
                            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                                super.onCodeAutoRetrievalTimeOut(s);
                                hideLoading();
                                showMessage("Hệ thống OTP đang mất kết nối" );
                            }
                        })
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);


    }

}
