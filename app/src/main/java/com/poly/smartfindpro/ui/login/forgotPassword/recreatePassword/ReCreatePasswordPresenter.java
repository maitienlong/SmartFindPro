package com.poly.smartfindpro.ui.login.forgotPassword.recreatePassword;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.databinding.FragmentRecreatePasswordBinding;
import com.poly.smartfindpro.ui.login.forgotPassword.ForgotPasswordContract;

public class ReCreatePasswordPresenter implements ReCreatePasswordContract.Presenter {
    private Context context;

    private ReCreatePasswordContract.ViewModel mViewModel;

    private FragmentRecreatePasswordBinding mBinding;

    public ReCreatePasswordPresenter(Context context, ReCreatePasswordContract.ViewModel mViewModel,FragmentRecreatePasswordBinding mBinding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void OnBackClick() {
        mViewModel.OnBackClick();
    }

    public TextWatcher OnMatchPassword() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Hihi", s.toString());
                if(!mBinding.edtNewPassword.getText().toString().trim().contains(s.toString())){
                    mBinding.edtRepassword.setError(context.getString(R.string.error_edt_dont_match));

                }else {
                }
            }
        };
    }
}
