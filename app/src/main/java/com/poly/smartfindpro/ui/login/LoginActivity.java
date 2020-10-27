package com.poly.smartfindpro.ui.login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseDataBindActivity<ActivityLoginBinding, LoginPresenter> implements LoginContract.ViewModel {

    private boolean isLogin = true;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        checkViewLogin();
        mBinding.btnChangeResign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = false;
                checkViewLogin();
            }
        });

        mBinding.btnChangeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = true;
                checkViewLogin();
            }
        });


    }

    @Override
    protected void initData() {

    }

    private void checkViewLogin() {
        if (isLogin) {
            mBinding.btnChangeLogin.setTextColor(getResources().getColor(R.color.background_login));
            mBinding.btnChangeResign.setTextColor(getResources().getColor(R.color.black));
            mBinding.tipRePassword.setVisibility(View.GONE);
            mBinding.btnAction.setText(this.getString(R.string.tv_dang_nhap));
        } else {
            mBinding.btnChangeLogin.setTextColor(getResources().getColor(R.color.black));
            mBinding.btnChangeResign.setTextColor(getResources().getColor(R.color.background_login));
            mBinding.tipRePassword.setVisibility(View.VISIBLE);
            mBinding.btnAction.setText(this.getString(R.string.tv_dang_ky));
        }
    }


}