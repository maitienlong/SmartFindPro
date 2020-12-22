package com.poly.smartfindpro.ui.searchProduct;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.callback.OnFragmentDataCallBack;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.res.Location;
import com.poly.smartfindpro.data.model.product.res.Product;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.ActivitySearchProductBinding;
import com.poly.smartfindpro.ui.MainActivity;
import com.poly.smartfindpro.ui.searchProduct.adapter.ProductDetailBottomAdapter;
import com.poly.smartfindpro.ui.searchProduct.filterProduct.FilterProductActivity;
import com.poly.smartfindpro.ui.searchProduct.map.MapsFragment;
import com.poly.smartfindpro.utils.BindingUtils;
import com.poly.smartfindpro.ui.searchProduct.adapter.ProductBottomAdapter;

import java.util.List;

public class SearchProductActivity extends BaseDataBindActivity<ActivitySearchProductBinding,
        SearchProductPresenter> implements SearchProductContract.ViewModel, OnFragmentDataCallBack {

    private static final String TAG = "MyTag";

    private BottomSheetBehavior bottomSheetBehavior;

    private LinearLayout bottomSheet;

    private RecyclerView rvProduct;

    ProductBottomAdapter adapter;

    private ProductDetailBottomAdapter adapterDetaiProduct;

    private TextView title;

    private TextView btn_fiter_nang_cao;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_product;
    }

    @Override
    protected void initView() {

        mPresenter = new SearchProductPresenter(getBaseContext(), this, mBinding);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Tìm kiếm");
        bottomSheet = findViewById(R.id.bottomSheet);
        rvProduct = findViewById(R.id.rvProduct);
        title = findViewById(R.id.tv_title_bts);
        btn_fiter_nang_cao = findViewById(R.id.btn_fiter_nang_cao);
        goToFragmentMapsCallBackData(R.id.fl_search, new MapsFragment(), null, this::onResult);

        // bottom sheet
        bottomSheetBehavior = bottomSheetBehavior.from(bottomSheet);

        bottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "ONCLickListner");

                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    Log.d(TAG, "state expand");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    Log.d(TAG, "state collapse");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }


    @Override
    protected void initData() {

        adapter = new ProductBottomAdapter(getBaseContext(), this);

    }


    @Override
    public void onShow(List<Products> products) {

        // send to map activity

        Bundle bundle = new Bundle();

        bundle.putString(Config.POST_BUNDEL_RES, new Gson().toJson(products));

        goToFragmentMapsCallBackData(R.id.fl_search, new MapsFragment(), bundle, this::onResult);

        // show bottom sheet
        adapter.setItemList(products);

        BindingUtils.setAdapter(rvProduct, adapter, true);

        title.setVisibility(View.VISIBLE);
        btn_fiter_nang_cao.setVisibility(View.VISIBLE);
        title.setText("Kết quả tìm kiếm");

        btn_fiter_nang_cao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(FilterProductActivity.class, bundle);
            }
        });

    }

    public void onShowResult(List<Products> productsList, int code) {
        if (code == 1) {
            Bundle bundle = new Bundle();

            bundle.putString(Config.POST_BUNDEL_RES, new Gson().toJson(productsList));

            goToFragmentMapsCallBackData(R.id.fl_search, new MapsFragment(), bundle, this::onResult);
        }
        title.setVisibility(View.GONE);
        btn_fiter_nang_cao.setVisibility(View.GONE);
        adapterDetaiProduct = new ProductDetailBottomAdapter(this, this);
        adapterDetaiProduct.setItemList(productsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvProduct.setLayoutManager(linearLayoutManager);
        rvProduct.setAdapter(adapterDetaiProduct);
    }

    @Override
    public void onResultAdapter(String tag) {
        mPresenter.onResultAdapter(tag);
    }

    @Override
    public void onSelectTypeFilter() {
        PopupMenu popupMenu = new PopupMenu(this, mBinding.typeFilter);
        popupMenu.inflate(R.menu.menu_choose_type_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.btn_filter_address:
                        mPresenter.filterAddress();
                        break;
                    case R.id.btn_filter_price:
                        mPresenter.filterPrice();
                        break;
                    case R.id.btn_filter_advanced:
                        mPresenter.filterAdvance();
                        break;
                }
                return false;
            }

        });
        popupMenu.show();
    }

    @Override
    public void filterAdvance(String jsonData) {
        Bundle bundle = new Bundle();
        bundle.putString(Config.POST_BUNDEL_RES, jsonData);
        openActivity(FilterProductActivity.class, bundle);
    }

    @Override
    public void onBackClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onSearch() {

    }


    @Override
    public void onResult(int resultCode, Intent intent) {

        Log.d("CheckCallback", resultCode + "");
        if (resultCode == Activity.RESULT_OK && intent.hasExtra("data")) {
            mPresenter.onDataCallBackMap(intent.getStringExtra("data"));
        }
    }
}