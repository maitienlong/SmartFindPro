package com.poly.smartfindpro.ui.login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.data.retrofit.MyRetrofit;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.ActivityLoginBinding;
import com.poly.smartfindpro.ui.login.loginFragment.LoginFragment;
import com.poly.smartfindpro.ui.login.registerFragment.RegisterFragment;


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
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            getSupportFragmentManager().beginTransaction().replace(R.id.fl_Login,new LoginFragment(),null);
//
            goToFragmentReplace(R.id.fl_Login, new LoginFragment(), null);
            mBinding.btnChangeLogin.setTextColor(getResources().getColor(R.color.background_login));
            mBinding.btnChangeResign.setTextColor(getResources().getColor(R.color.black));
        } else {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            getSupportFragmentManager().beginTransaction().replace(R.id.fl_Login,new RegisterFragment(),null);
            goToFragmentReplace(R.id.fl_Login, new RegisterFragment(), null);
            mBinding.btnChangeResign.setTextColor(getResources().getColor(R.color.background_login));
            mBinding.btnChangeLogin.setTextColor(getResources().getColor(R.color.black));
        }
    }


}