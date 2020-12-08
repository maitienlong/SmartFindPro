package com.poly.smartfindpro.ui.searchProduct;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.ActivitySearchProductBinding;
import com.poly.smartfindpro.ui.searchProduct.map.MapsFragment;
import com.poly.smartfindpro.utils.BindingUtils;

import java.util.List;

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

        mPresenter = new SearchProductPresenter(getBaseContext(), this, mBinding);
        mBinding.setPresenter(mPresenter);

        bottomSheet = findViewById(R.id.bottomSheet);
        rvProduct = findViewById(R.id.rvProduct);

        goToFragment(R.id.fl_search, new MapsFragment(), null);

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
        adapter = new ProductBottomAdapter(getBaseContext());

    }


    @Override
    public void onShow(List<Products> products) {
        Log.d("checkList", products.size() + "");
        adapter.setItemList(products);
        BindingUtils.setAdapter(rvProduct, adapter, true);
    }



}