package com.poly.smartfindpro.ui.searchProduct.filterProduct;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.home.res.Product;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.ActivityFilterProductBinding;
import com.poly.smartfindpro.databinding.ActivitySearchProductBinding;
import com.poly.smartfindpro.ui.home.adapter.HomeAdapter;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;
import com.poly.smartfindpro.ui.searchProduct.SearchProductContract;
import com.poly.smartfindpro.ui.searchProduct.SearchProductPresenter;
import com.poly.smartfindpro.ui.searchProduct.adapter.FilterProductAdapter;
import com.poly.smartfindpro.ui.searchProduct.adapter.ProductBottomAdapter;
import com.poly.smartfindpro.utils.BindingUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FilterProductActivity extends BaseDataBindActivity<ActivityFilterProductBinding,
        FilterProductPresenter> implements FilterProductContact.ViewModel {
    FilterProductAdapter adapter;

    private List<Products>  products;

    private Button btnNhaTro, btnChungCu, btnNguyenCan, btnOGhep, btn_filter;

    private EditText edt_amount_person, edt_electricity_bill, edt_water_bill;

    private SeekBar snb_price;

    private RadioButton btnNam, btnNu, btnTatCa;

    private String category = "";

    private String mGender = "";

    private int mPrice = 1;

    private int mPriceElect = 1;

    private int mPriceWatter = 1;

    private int soLuong = 1;

    private RecyclerView rc_utilities;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_filter_product;
    }

    private void getData() {
        Type type = new TypeToken<List<Products>>() {
        }.getType();
        products = new ArrayList<>();
        products = new Gson().fromJson(getIntent().getStringExtra(Config.POST_BUNDEL_RES), type);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initView() {
        btnNhaTro = findViewById(R.id.btn_nha_tro);
        btnChungCu = findViewById(R.id.btn_chung_cu);
        btnNguyenCan = findViewById(R.id.btn_nguyen_can);
        btnOGhep = findViewById(R.id.btn_o_ghep);
        edt_amount_person = findViewById(R.id.edt_amount_person);
        snb_price = findViewById(R.id.snb_price);
        btnTatCa = findViewById(R.id.rbAll);
        btnNam = findViewById(R.id.rbMale);
        btnNu = findViewById(R.id.rbFemale);
        btn_filter = findViewById(R.id.btn_filter);
        edt_electricity_bill = findViewById(R.id.edt_electricity_bill);
        edt_water_bill = findViewById(R.id.edt_water_bill);
        rc_utilities = findViewById(R.id.rc_utilities);

        mPresenter = new FilterProductPresenter(this, this, mBinding);
        mBinding.setPresenter(mPresenter);

        btnNhaTro.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                btnNhaTro.setBackgroundTintList(getColorStateList(R.color.background));
                btnChungCu.setPressed(false);
                btnNguyenCan.setPressed(false);
                btnOGhep.setPressed(false);
                category = btnNhaTro.getText().toString();
            }
        });

        btnChungCu.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                btnNhaTro.setPressed(false);
                btnChungCu.setBackgroundTintList(getColorStateList(R.color.background));
                btnNguyenCan.setPressed(false);
                btnOGhep.setPressed(false);
                category = btnChungCu.getText().toString();
            }
        });

        btnNguyenCan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                btnNhaTro.setPressed(false);
                btnChungCu.setPressed(false);
                btnNguyenCan.setBackgroundTintList(getColorStateList(R.color.background));
                btnOGhep.setPressed(false);
                category = btnNguyenCan.getText().toString();
            }
        });

        btnOGhep.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                btnNhaTro.setPressed(false);
                btnChungCu.setPressed(false);
                btnNguyenCan.setPressed(false);
                btnOGhep.setBackgroundTintList(getColorStateList(R.color.background));
                category = btnOGhep.getText().toString();
            }
        });

        if (btnNam.isChecked()) {
            mGender = btnNam.getText().toString();
        } else if (btnNu.isChecked()) {
            mGender = btnNu.getText().toString();
        } else if (btnTatCa.isChecked()) {
            mGender = btnTatCa.getText().toString();
        }

        snb_price.setMin(1000000);
        snb_price.setMax(100000000);

        mPriceElect = Integer.valueOf(edt_electricity_bill.getText().toString());
        mPriceWatter = Integer.valueOf(edt_water_bill.getText().toString());

        soLuong = Integer.valueOf(edt_amount_person.getText().toString());
        snb_price.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPrice = seekBar.getProgress();
                Toast.makeText(FilterProductActivity.this, mPrice + "", Toast.LENGTH_SHORT).show();
            }
        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setData(category, soLuong, mPrice, mPriceElect, mPriceWatter, mGender);
            }
        });
    }

    @Override
    protected void initData() {
        adapter = new FilterProductAdapter(getBaseContext());
        mPresenter.setProducts(products);
    }

    @Override
    public void onShow(List<Products> products) {
        adapter.setListItem(products);
        BindingUtils.setAdapter(mBinding.rvResult, adapter, true);
    }

    @Override
    public void onClickFilter() {

    }

    @Override
    public void onShowMsg(String msg) {
        showMessage(msg);
    }


}