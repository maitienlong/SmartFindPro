package com.poly.smartfindpro.ui.searchProduct.filterProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.data.model.home.res.Product;
import com.poly.smartfindpro.databinding.ActivityFilterProductBinding;
import com.poly.smartfindpro.databinding.ActivitySearchProductBinding;
import com.poly.smartfindpro.ui.home.adapter.HomeAdapter;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;
import com.poly.smartfindpro.ui.searchProduct.SearchProductContract;
import com.poly.smartfindpro.ui.searchProduct.SearchProductPresenter;
import com.poly.smartfindpro.ui.searchProduct.adapter.FilterProductAdapter;
import com.poly.smartfindpro.ui.searchProduct.adapter.ProductBottomAdapter;
import com.poly.smartfindpro.utils.BindingUtils;

import java.util.List;

public class FilterProductActivity extends BaseDataBindActivity<ActivityFilterProductBinding,
        FilterProductPresenter> implements FilterProductContact.ViewModel {
    FilterProductAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_filter_product;
    }

    @Override
    protected void initView() {
        mPresenter = new FilterProductPresenter(getBaseContext(), this, mBinding);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void initData() {
        adapter = new FilterProductAdapter(getBaseContext());
    }

    @Override
    public void onShow(List<Product> products) {
        adapter.setListItem(products);
        BindingUtils.setAdapter(mBinding.rvResult, adapter, true);
    }

    @Override
    public void onClickFilter() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new InforPostFragment ()).commit();}

}