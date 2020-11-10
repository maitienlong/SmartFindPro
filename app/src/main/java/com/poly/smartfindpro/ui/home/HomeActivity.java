package com.poly.smartfindpro.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityHomeBinding;
import com.poly.smartfindpro.databinding.ActivityMainBinding;
import com.poly.smartfindpro.ui.MainPresenter;
import com.poly.smartfindpro.utils.BindingUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseDataBindActivity<ActivityHomeBinding,
        HomePresenter> implements HomeContract.ViewModel {
    private HomeAdapter homeAdapter;
    private ArrayList<Product> product = new ArrayList<>();
    private ArrayList<String> productTest = new ArrayList<String>();

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
        homeAdapter = new HomeAdapter(this);
        for (int i = 0; i < 10; i++) {
            productTest.add("namdeptrai" + i);
        }
        homeAdapter.setListItemTest(productTest);

        BindingUtils.setAdapter(mBinding.rvList, homeAdapter, false);
        // mPresenter. ... goi nhung thanh phan ben Presenter

    }

    @Override
    public void onBackClick() {
        // Cai nay la o ben Controctor khai bao
    }
}