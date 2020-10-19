package com.poly.smartfindpro.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityHomeBinding;
import com.poly.smartfindpro.databinding.ActivityMainBinding;
import com.poly.smartfindpro.ui.MainPresenter;

public class HomeActivity extends BaseDataBindActivity<ActivityHomeBinding,
        HomePresenter> implements HomeContract.ViewModel {

        @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {

     //  mBinding. ...  goi thanh phan giao dien


    }

    @Override
    protected void initData() {

       // mPresenter. ... goi nhung thanh phan ben Presenter

    }

    @Override
    public void onBackClick() {
        // Cai nay la o ben Controctor khai bao
    }
}