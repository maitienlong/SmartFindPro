package com.poly.smartfindpro.ui.login.registerFragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.databinding.FragmentRegisterBinding;
import com.poly.smartfindpro.ui.login.otp.ConfirmOTPFragment;

import java.util.concurrent.TimeUnit;

public class RegisterFragment extends BaseDataBindFragment<FragmentRegisterBinding, RegisterPresenter> implements RegisterContract.ViewModel {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {

        mBinding.btnAction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().goToFragmentReplace(R.id.fl_Login, new ConfirmOTPFragment("",""), null);
            }
        });

    }

    @Override
    protected void initData() {
        if (mBinding.edtAccountNumber.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Bạn chưa nhập số điên thoại", Toast.LENGTH_SHORT).show();
            return;
        }
        mBinding.btnAction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+84" + mBinding.edtAccountNumber.getText().toString(),
                        60, TimeUnit.SECONDS, getActivity(),
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(getActivity(), "hỏng nhé", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                //   super.onCodeSent(verificationId, forceResendingToken);
//                                Intent intent = new Intent(, getActivity());
//                                intent.putExtra("phoneNumber", mBinding.edtAccountNumber.getText().toString());
//                                intent.putExtra("verify", verificationId);
//                                Toast.makeText(getActivity(), "đã vào đây nhưng mà đ chạy", Toast.LENGTH_SHORT).show();

                                if (onReceivedOTP != null) {
                                    onReceivedOTP.onReceivedOTP(mBinding.edtAccountNumber.getText().toString(), verificationId);
                                }
                            }
                        }
                );

            }
        });
    }

    public interface OnReceivedOTP {
        void onReceivedOTP(String phoneNumber, String verificationId);
    }

    public OnReceivedOTP onReceivedOTP;

    public void setOnReceivedOTP(OnReceivedOTP onReceivedOTP) {
        this.onReceivedOTP = onReceivedOTP;
    }


}

