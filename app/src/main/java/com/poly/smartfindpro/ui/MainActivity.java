package com.poly.smartfindpro.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityMainBinding;

public class MainActivity extends BaseDataBindActivity<ActivityMainBinding,
        MainPresenter> implements MainContract.ViewModel  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void openFragment() {

    }
}