package com.poly.smartfindpro.ui.user.ui;

import androidx.annotation.RequiresApi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityUserBinding;
import com.poly.smartfindpro.ui.user.constract.UserContract;
import com.poly.smartfindpro.ui.user.presenter.UserPresenter;

import java.util.ArrayList;

public class UserActivity extends BaseDataBindActivity<ActivityUserBinding,
        UserPresenter> implements UserContract.ViewModel  {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initView() {
        ArrayList<View> listViewClick = new ArrayList<View>();
        listViewClick.add(mBinding.displayUser);
        listViewClick.add(mBinding.displaySettings);
        listViewClick.add(mBinding.displayRules);
        listViewClick.add(mBinding.displayAsistentAndFeedback);
        listViewClick.add(mBinding.displayLogOut);

        setListViewOnClick(listViewClick);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void openFragment() {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.display_user:
                startActivity(new Intent(this, UserDetailActivity.class));
                break;
        }
    }
}