package com.poly.smartfindpro.ui.login.otp;

import android.os.Bundle;
import android.view.View;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.register.regisRequest.RegisterRequest;
import com.poly.smartfindpro.databinding.FragmentConfirmOtpBinding;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.login.createPassword.CreatePasswordFragment;
import com.poly.smartfindpro.ui.login.registerFragment.RegisterPresenter;

public class ConfirmOTPFragment extends BaseDataBindFragment<FragmentConfirmOtpBinding, ConfirmOTPPresenter> implements ConfirmOTPContract.ViewModel {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_otp;
    }


    @Override
    protected void initView() {

        mPresenter = new ConfirmOTPPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);

    }

    @Override
    protected void initData() {

    }
    private void onNext(){
        Bundle bundle = new Bundle();
        bundle.putString(Config.POST_BUNDEL_RES, getArguments().getString(Config.POST_BUNDEL_RES));
        getBaseActivity().goToFragmentReplace(R.id.fl_Login, new CreatePasswordFragment(), bundle);
    }

    @Override
    public void onClickConfirm() {
    if (mBinding.edtOtp.getText().toString().equals("123456")){
        onNext();
    }else if (mBinding.edtOtp.getText().toString().equals("")){
        showMessage("vui long nhap OTP");
    }else {
        showMessage("OTP khong chinh xac");
    }
    }
}
