package com.poly.smartfindpro.ui.searchProduct;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.data.model.product.Product;
import com.poly.smartfindpro.data.model.product.Product_;
import com.poly.smartfindpro.data.retrofit.ListServices;
import com.poly.smartfindpro.data.retrofit.RetrofitConnect;
import com.poly.smartfindpro.databinding.ActivitySearchProductBinding;
import com.poly.smartfindpro.ui.listProduct.ProductBottomAdapter;
import com.poly.smartfindpro.utils.BindingUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchProductActivity extends BaseDataBindActivity<ActivitySearchProductBinding,
        SearchProductPresenter> implements SearchProductContract.ViewModel {

    private static final String TAG = "MyTag";
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout bottomSheet;
    private RecyclerView rvProduct;

    ProductBottomAdapter adapter;

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
        adapter = new ProductBottomAdapter(getBaseContext());
        bottomSheet = findViewById(R.id.bottomSheet);
        rvProduct = findViewById(R.id.rvProduct);
        bottomSheetBehavior = bottomSheetBehavior.from(bottomSheet);
        mBinding.imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });


    }

    @Override
    protected void initData() {
        mPresenter = new SearchProductPresenter(this, this);
        mBinding.setPresenter(mPresenter);


    }

    @Override
    public void openFragment() {

    }

    @Override
    public void onShow(List<Product_> products) {
        adapter.setItemList(products);
        BindingUtils.setAdapter(rvProduct, adapter, true);
    }
}