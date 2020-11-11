package com.poly.smartfindpro.ui.selectArea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityHomeBinding;
import com.poly.smartfindpro.ui.home.HomePresenter;

public class SelectAreaActivity extends BaseDataBindActivity<ActivityHomeBinding,
        HomePresenter> implements SelectAreaContract.ViewModel{


    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_area;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}