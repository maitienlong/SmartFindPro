package com.poly.smartfindpro.ui.detailpost;

import android.content.Intent;

import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.ActivityInformationPostBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetailPostActivity extends BaseDataBindActivity<ActivityInformationPostBinding, DetailPostPresenter>
        implements DetailPostContact.ViewModel {
    private Products mProduct;
    private DetailImageAdapter adapter;
    private List<String> imageList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_post;
    }

    @Override
    protected void initView() {
        getData();
        mPresenter = new DetailPostPresenter(this, this);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void initData() {
        adapter = new DetailImageAdapter(this, imageList);
        mBinding.rvListImage.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        mBinding.rvListImage.setAdapter(adapter);
        mPresenter.setData(mProduct);
    }

    private void getData() {
        Type type = new TypeToken<Products>() {
        }.getType();
        Intent intent = getIntent();
        mProduct = new Products();
        mProduct = new Gson().fromJson(intent.getStringExtra(Config.POST_BUNDEL_RES),type);
    }
}