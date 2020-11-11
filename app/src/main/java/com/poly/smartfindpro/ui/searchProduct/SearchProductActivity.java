package com.poly.smartfindpro.ui.searchProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityHomeBinding;
import com.poly.smartfindpro.databinding.ActivitySearchProductBinding;
import com.poly.smartfindpro.ui.home.HomeContract;
import com.poly.smartfindpro.ui.home.HomePresenter;

public class SearchProductActivity extends BaseDataBindActivity<ActivitySearchProductBinding,
        SearchProductPresenter> implements SearchProductContract.ViewModel  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_product;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter = new SearchProductPresenter(this,this);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    public void openFragment() {

    }
}