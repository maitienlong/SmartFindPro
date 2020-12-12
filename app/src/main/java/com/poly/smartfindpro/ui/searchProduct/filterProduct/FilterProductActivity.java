package com.poly.smartfindpro.ui.searchProduct.filterProduct;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.ActivityFilterProductBinding;
import com.poly.smartfindpro.ui.searchProduct.adapter.FilterProductAdapter;
import com.poly.smartfindpro.ui.searchProduct.adapter.SpinnerCatalory;
import com.poly.smartfindpro.utils.BindingUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FilterProductActivity extends BaseDataBindActivity<ActivityFilterProductBinding,
        FilterProductPresenter> implements FilterProductContact.ViewModel {
    FilterProductAdapter adapter;

    private List<Products> mListProduct;

    private Button btn_filter;

    private EditText edt_amount_person, edt_electricity_bill, edt_water_bill;

    private SeekBar snb_price;

    private RadioButton btnNam, btnNu, btnTatCa;

    private TextView tv_maxTien;

    private String category = "";

    private String mGender = "";

    private int mPrice = 1;

    private int mPriceElect = 1;

    private int mPriceWatter = 1;

    private int soLuong = 1;

    private Spinner spnTheLoai;

    private RecyclerView rc_utilities;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_filter_product;
    }

    private void getData() {
        Type type = new TypeToken<List<Products>>() {
        }.getType();
        if (getIntent() != null) {
            mListProduct = new ArrayList<>();
            mListProduct = new Gson().fromJson(getIntent().getStringExtra(Config.POST_BUNDEL_RES), type);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initView() {
        getData();
        edt_amount_person = findViewById(R.id.edt_amount_person);
        snb_price = findViewById(R.id.snb_price);
        btnTatCa = findViewById(R.id.rbAll);
        btnNam = findViewById(R.id.rbMale);
        btnNu = findViewById(R.id.rbFemale);
        btn_filter = findViewById(R.id.btn_filter);
        edt_electricity_bill = findViewById(R.id.edt_electricity_bill);
        edt_water_bill = findViewById(R.id.edt_water_bill);
        rc_utilities = findViewById(R.id.rc_utilities);
        spnTheLoai = findViewById(R.id.spnTheLoai);
        tv_maxTien = findViewById(R.id.tv_maxTien);
        mPresenter = new FilterProductPresenter(this, this, mBinding);
        mBinding.setPresenter(mPresenter);

        List<String> listCatalory = new ArrayList<>();
        listCatalory.add("");
        listCatalory.add("Nhà trọ");
        listCatalory.add("Nguyên căn");
        listCatalory.add("Chung cư");
        listCatalory.add("Ở ghép");

        SpinnerCatalory spinnerCatalory = new SpinnerCatalory(this, listCatalory);
        spnTheLoai.setAdapter(spinnerCatalory);

        snb_price.setMin(0);
        snb_price.setMax(100);

        // the loai
        spnTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                if (!item.equals("")) {
                    mPresenter.setData(item, 0);
                } else {
                    Toast.makeText(FilterProductActivity.this, "Bạn không chọn thể loại", Toast.LENGTH_SHORT).show();
                    mPresenter.setData(item, 0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // gioi tinh
        if (btnNam.isChecked()) {
            mPresenter.setData(btnTatCa.getText().toString(), 5);
        } else if (btnNu.isChecked()) {
            mPresenter.setData(btnTatCa.getText().toString(), 5);
        } else if (btnTatCa.isChecked()) {
            mPresenter.setData(btnTatCa.getText().toString(), 5);
        }

        // gia tien dien
        edt_electricity_bill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null){
                    mPresenter.setData(s.toString(), 5);
                }else {
                    mPresenter.setData("", 5);
                }

            }
        });

        // gia tien nuoc
        edt_water_bill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null){
                    mPresenter.setData(s.toString(), 6);
                }else {
                    mPresenter.setData("", 6);
                }

            }
        });

        // so luong nguoi
        edt_amount_person.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null){
                    mPresenter.setData(s.toString(), 1);
                }else {
                    mPresenter.setData("", 1);
                }

            }
        });

        // gia tien
        snb_price.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_maxTien.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_maxTien.setText(seekBar.getProgress()*500000+"");
                    }
                });
                if(seekBar.getProgress() > 0){
                    mPresenter.setData(String.valueOf(seekBar.getProgress()*500000), 3);
                }else {
                    mPresenter.setData("", 3);
                }



            }
        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initData() {
        adapter = new FilterProductAdapter(getBaseContext());
        mPresenter.setProducts(mListProduct);
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

    @Override
    public void onBackClick() {
        finish();
    }


}