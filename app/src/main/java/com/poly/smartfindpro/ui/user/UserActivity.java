package com.poly.smartfindpro.ui.user;

import androidx.annotation.RequiresApi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityUserBinding;
import com.poly.smartfindpro.ui.login.LoginActivity;
import com.poly.smartfindpro.ui.user.rules.RulesFragment;
import com.poly.smartfindpro.ui.user.userFragment.UserFragment;

import java.util.ArrayList;

public class UserActivity extends BaseDataBindActivity<ActivityUserBinding, UserPresenter> implements UserContract.ViewModel {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void initView() {
     goToFragment(R.id.fl_user,new UserFragment(),null);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void openFragment() {

    }

}