package com.poly.smartfindpro.ui.searchProduct;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
                bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        switch (newState) {
                            case BottomSheetBehavior.STATE_DRAGGING:
                                Log.d("BottomSheetCallback", "BottomSheetBehavior.STATE_DRAGGING");
                                Toast.makeText(getBaseContext(), "STATE_DRAGGING", Toast.LENGTH_SHORT).show();
                                break;
                            case BottomSheetBehavior.STATE_SETTLING:
                                Log.d("BottomSheetCallback", "BottomSheetBehavior.STATE_SETTLING");
                                Toast.makeText(getBaseContext(), "STATE_SETTLING", Toast.LENGTH_SHORT).show();

                                break;
                            case BottomSheetBehavior.STATE_EXPANDED:
                                Log.d("BottomSheetCallback", "BottomSheetBehavior.STATE_EXPANDED");
                                Toast.makeText(getBaseContext(), "STATE_EXPANDED", Toast.LENGTH_SHORT).show();

                                break;
                            case BottomSheetBehavior.STATE_COLLAPSED:
                                Log.d("BottomSheetCallback", "BottomSheetBehavior.STATE_COLLAPSED");
                                Toast.makeText(getBaseContext(), "STATE_COLLAPSED", Toast.LENGTH_SHORT).show();

                                break;
                            case BottomSheetBehavior.STATE_HIDDEN:
                                Log.d("BottomSheetCallback", "BottomSheetBehavior.STATE_HIDDEN");
                                Toast.makeText(getBaseContext(), "STATE_HIDDEN", Toast.LENGTH_SHORT).show();

                                break;

                        }
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                        Log.d("BottomSheetCallback", "slideOffset: " + slideOffset);
//                        boolean drawer = getSupportFragmentManager().getBackStackEntryCount() == 0;
//                        ObjectAnimator.ofFloat(drawerArrow, "progress", drawer ? 0 : 1).start();
                    }
                });
            }
        });


        bottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "ONCLickListner");

                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    Log.d(TAG,"state expand");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    Log.d(TAG,"state collapse");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
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