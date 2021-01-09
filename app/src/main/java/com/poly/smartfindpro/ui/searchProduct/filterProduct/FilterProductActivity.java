package com.poly.smartfindpro.ui.searchProduct.filterProduct;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
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

    private FilterTool mFilterTool;

    private Button btn_filter;

    private EditText edt_amount_person, edt_electricity_bill, edt_water_bill;

    private SeekBar snb_price;

    private RadioButton btnNam, btnNu, btnTatCa;

    private TextView tv_maxTien;

    private Spinner spnTheLoai;

    private RecyclerView rc_utilities;

    private BottomSheetBehavior bottomSheetBehavior;

    private LinearLayout bottomSheet;

    private List<String> mPriority;


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
        mPresenter = new FilterProductPresenter(this, this, mBinding);
        mBinding.setPresenter(mPresenter);
        getData();

        mFilterTool = new FilterTool();

        edt_amount_person = findViewById(R.id.edt_amount_person);
        snb_price = findViewById(R.id.snb_price);
        btnTatCa = findViewById(R.id.rbAllSearch);
        btnNam = findViewById(R.id.rbMaleSearch);
        btnNu = findViewById(R.id.rbFemaleSearch);
        btn_filter = findViewById(R.id.btn_filter);
        edt_electricity_bill = findViewById(R.id.edt_electricity_bill);
        edt_water_bill = findViewById(R.id.edt_water_bill);
        rc_utilities = findViewById(R.id.rc_utilities);
        spnTheLoai = findViewById(R.id.spnTheLoai);
        tv_maxTien = findViewById(R.id.tv_maxTien);
        bottomSheet = findViewById(R.id.bottomSheetFilter);


        List<String> listCatalory = new ArrayList<>();
        listCatalory.add("--- Chọn thể loại ---");
        listCatalory.add("Nhà trọ");
        listCatalory.add("Nguyên căn");
        listCatalory.add("Chung cư");
        listCatalory.add("Ở ghép");

        SpinnerCatalory spinnerCatalory = new SpinnerCatalory(this, listCatalory);
        spnTheLoai.setAdapter(spinnerCatalory);

        snb_price.setMin(0);
        snb_price.setMax(100);

        bottomSheetBehavior = bottomSheetBehavior.from(bottomSheet);
        mBinding.btnFilterOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                AlertDialog.Builder builder = new AlertDialog.Builder(FilterProductActivity.this);

                View alert = LayoutInflater.from(FilterProductActivity.this).inflate(R.layout.dialog_priority, null);

                builder.setView(alert);

                boolean[] trangThai = {false, false, false, false, false, false};
                int[] viTri = {0, 1, 2, 3, 4, 5, 6};


                Button btnTheLoai = alert.findViewById(R.id.btnTheLoai);

                Button btnGia = alert.findViewById(R.id.btnGia);

                Button btnSoLuong = alert.findViewById(R.id.btnSoLuong);

                Button btnGioiTinh = alert.findViewById(R.id.btnGioiTinh);

                Button btnTienDien = alert.findViewById(R.id.btnTienDien);

                Button btnTienNuoc = alert.findViewById(R.id.btnTienNuoc);

                mPriority = new ArrayList<>();

                btnTheLoai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (trangThai[0] == false) {
                            mPriority.add("theloai");
                            viTri[0] = mPriority.size() - 1;
                            trangThai[0] = true;
                            btnTheLoai.setBackgroundTintList(getColorStateList(R.color.background));
                            btnTheLoai.setText("Thể loại " + "(" + mPriority.size() + ")");
                        } else if (trangThai[0] == true) {
                            if (viTri[0] == (mPriority.size() - 1)) {
                                mPriority.remove("theloai");
                                viTri[0] = mPriority.size() - 1;
                                trangThai[0] = false;
                                btnTheLoai.setBackgroundTintList(getColorStateList(R.color.gray2));
                                btnTheLoai.setText("Thể loại");
                            } else {
                                Toast.makeText(FilterProductActivity.this, "Bạn phải bỏ chọn theo lần lượt", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                btnGia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (trangThai[1] == false) {
                            mPriority.add("giatien");
                            viTri[1] = mPriority.size() - 1;
                            trangThai[1] = true;
                            btnGia.setBackgroundTintList(getColorStateList(R.color.background));
                            btnGia.setText("Giá tiền " + "(" + mPriority.size() + ")");
                        } else if (trangThai[1] == true) {
                            if (viTri[1] == (mPriority.size() - 1)) {
                                mPriority.remove("giatien");
                                viTri[1] = mPriority.size() - 1;
                                trangThai[1] = false;
                                btnGia.setBackgroundTintList(getColorStateList(R.color.gray2));
                                btnGia.setText("Giá tiền");
                            } else {
                                Toast.makeText(FilterProductActivity.this, "Bạn phải bỏ chọn theo lần lượt", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                btnSoLuong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (trangThai[2] == false) {
                            mPriority.add("soluong");
                            viTri[2] = mPriority.size() - 1;
                            trangThai[2] = true;
                            btnSoLuong.setBackgroundTintList(getColorStateList(R.color.background));
                            btnSoLuong.setText("Số lượng " + "(" + mPriority.size() + ")");
                        } else if (trangThai[2] == true) {
                            if (viTri[2] == (mPriority.size() - 1)) {
                                mPriority.remove("soluong");
                                viTri[2] = mPriority.size() - 1;
                                trangThai[2] = false;
                                btnSoLuong.setBackgroundTintList(getColorStateList(R.color.gray2));
                                btnSoLuong.setText("Số lượng");
                            } else {
                                Toast.makeText(FilterProductActivity.this, "Bạn phải bỏ chọn theo lần lượt", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                btnGioiTinh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (trangThai[3] == false) {
                            mPriority.add("gioitinh");
                            viTri[3] = mPriority.size() - 1;
                            trangThai[3] = true;
                            btnGioiTinh.setBackgroundTintList(getColorStateList(R.color.background));
                            btnGioiTinh.setText("Giới tính " + "(" + mPriority.size() + ")");
                        } else if (trangThai[3] == true) {
                            if (viTri[3] == (mPriority.size() - 1)) {
                                mPriority.remove("gioitinh");
                                viTri[3] = mPriority.size() - 1;
                                trangThai[3] = false;
                                btnGioiTinh.setBackgroundTintList(getColorStateList(R.color.gray2));
                                btnGioiTinh.setText("Giới tính");
                            } else {
                                Toast.makeText(FilterProductActivity.this, "Bạn phải bỏ chọn theo lần lượt", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                btnTienDien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (trangThai[4] == false) {
                            mPriority.add("tiendien");
                            viTri[4] = mPriority.size() - 1;
                            trangThai[4] = true;
                            btnTienDien.setBackgroundTintList(getColorStateList(R.color.background));
                            btnTienDien.setText("Tiền điện " + "(" + mPriority.size() + ")");
                        } else if (trangThai[4] == true) {
                            if (viTri[4] == (mPriority.size() - 1)) {
                                mPriority.remove("tiendien");
                                viTri[4] = mPriority.size() - 1;
                                trangThai[4] = false;
                                btnTienDien.setBackgroundTintList(getColorStateList(R.color.gray2));
                                btnTienDien.setText("Tiền điện");
                            } else {
                                Toast.makeText(FilterProductActivity.this, "Bạn phải bỏ chọn theo lần lượt", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                btnTienNuoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (trangThai[5] == false) {
                            mPriority.add("tiennuoc");
                            viTri[5] = mPriority.size() - 1;
                            trangThai[5] = true;
                            btnTienNuoc.setBackgroundTintList(getColorStateList(R.color.background));
                            btnTienNuoc.setText("Tiền nước " + "(" + mPriority.size() + ")");
                        } else if (trangThai[5] == true) {
                            if (viTri[5] == (mPriority.size() - 1)) {
                                mPriority.remove("tiennuoc");
                                viTri[5] = mPriority.size() - 1;
                                trangThai[5] = false;
                                btnTienNuoc.setBackgroundTintList(getColorStateList(R.color.gray2));
                                btnTienNuoc.setText("Tiền nước");
                            } else {
                                Toast.makeText(FilterProductActivity.this, "Bạn phải bỏ chọn theo lần lượt", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                builder.create().show();
            }
        });

        // the loai
        spnTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                mFilterTool.setTheLoai(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // gioi tinh
        btnNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterTool.setGioiTinh(btnNam.getText().toString());
            }
        });

        btnNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterTool.setGioiTinh(btnNu.getText().toString());
            }
        });

        btnTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterTool.setGioiTinh(btnTatCa.getText().toString());
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
                        tv_maxTien.setText(seekBar.getProgress() * 500000 + "");
                    }
                });
                mFilterTool.setGia(seekBar.getProgress() * 500000);
            }
        });

        // xac nhan
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_amount_person.getText().toString().trim().isEmpty()) {
                    showMessage("Số lượng người không được chọn, sẽ bỏ qua lọc số lượng");
                    mFilterTool.setSoLuongNguoi(0);
                } else if (edt_electricity_bill.getText().toString().trim().isEmpty()) {
                    showMessage("Số tiền điện không được chọn, sẽ bỏ qua lọc số lượng");
                    mFilterTool.setTienDien(0);
                } else if (edt_water_bill.getText().toString().trim().isEmpty()) {
                    showMessage("Số tiền nước không được chọn, sẽ bỏ qua lọc số lượng");
                    mFilterTool.setTienNuoc(0);
                } else if (mPriority == null) {
                    showMessage("Bạn chưa lựa chọn ưu tiên lọc");
                } else {
                    mFilterTool.setTienDien(Integer.parseInt(edt_electricity_bill.getText().toString()));

                    mFilterTool.setTienNuoc(Integer.parseInt(edt_electricity_bill.getText().toString()));

                    mFilterTool.setSoLuongNguoi(Integer.parseInt(edt_amount_person.getText().toString()));

                    mPresenter.onClickFilter(mFilterTool, mPriority);

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.setProducts(mListProduct);
        mPresenter = new FilterProductPresenter(this, this, mBinding);
        mBinding.setPresenter(mPresenter);
        adapter = new FilterProductAdapter(this);

        onShow(mListProduct);
    }

    @Override
    public void onShow(List<Products> products) {
        adapter.setListItem(products);
        BindingUtils.setAdapter(mBinding.rvResult, adapter, true);
        Log.d("checkCount", String.valueOf(products.size()));
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