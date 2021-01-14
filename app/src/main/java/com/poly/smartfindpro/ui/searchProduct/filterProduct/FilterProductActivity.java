package com.poly.smartfindpro.ui.searchProduct.filterProduct;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    private Button btn_filter;

    private EditText edt_amount_person;

    private SeekBar snb_price;

    private RadioButton btnNam, btnNu, btnTatCa;

    private RadioGroup rgGender;

    private TextView tv_maxTien, tv_gioitinh, tv_giaca, tv_songuoi, tv_thongtin;

    private Spinner spnTheLoai;

    private RecyclerView rc_utilities;

    private BottomSheetBehavior bottomSheetBehavior;

    private LinearLayout bottomSheet, ln_theloai, ln_chongia;

    private List<String> mPriority;

    private List<Priority> mPriorityList;


    @Override
    protected int getLayoutId() {
        Config.setStatusBarGradiant(this);
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

        mPriorityList = new ArrayList<>();

        edt_amount_person = findViewById(R.id.edt_amount_person);
        snb_price = findViewById(R.id.snb_price);
        btnTatCa = findViewById(R.id.rbAllSearch);
        btnNam = findViewById(R.id.rbMaleSearch);
        btnNu = findViewById(R.id.rbFemaleSearch);
        btn_filter = findViewById(R.id.btn_filter);
        rc_utilities = findViewById(R.id.rc_utilities);
        spnTheLoai = findViewById(R.id.spnTheLoai);
        tv_maxTien = findViewById(R.id.tv_maxTien);
        bottomSheet = findViewById(R.id.bottomSheetFilter);
        rgGender = findViewById(R.id.rgGender);

        tv_gioitinh = findViewById(R.id.tv_gioitinh);
        tv_giaca = findViewById(R.id.tv_giaca);
        tv_songuoi = findViewById(R.id.tv_songuoi);
        tv_thongtin = findViewById(R.id.tv_thongtin);
        ln_theloai = findViewById(R.id.ln_theloai);
        ln_chongia = findViewById(R.id.ln_chongia);

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

        //
        mBinding.btnFilterOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(FilterProductActivity.this, mBinding.btnFilterOption);
                popupMenu.inflate(R.menu.filter);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.btn_filter_all:
                                onShowLayount(1, true);
                                onShowLayount(2, true);
                                onShowLayount(3, true);
                                onShowLayount(4, true);
                                mPriority = new ArrayList<>();
                                mPriorityList = new ArrayList<>();
                                Priority theLoai = new Priority(0, "");
                                Priority giatien = new Priority(2, "");
                                Priority gioitinh = new Priority(3, "");
                                Priority soluong = new Priority(4, "");
                                mPriorityList.add(theLoai);
                                mPriorityList.add(giatien);
                                mPriorityList.add(gioitinh);
                                mPriorityList.add(soluong);

                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                break;
                            case R.id.btn_filter_custom:
                                onShowLayount(1, false);
                                onShowLayount(2, false);
                                onShowLayount(3, false);
                                onShowLayount(4, false);

                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                                AlertDialog.Builder builder = new AlertDialog.Builder(FilterProductActivity.this);

                                View alert = LayoutInflater.from(FilterProductActivity.this).inflate(R.layout.dialog_priority, null);

                                builder.setView(alert);

                                boolean[] trangThai = {false, false, false, false, false, false};
                                int[] viTri = {0, 1, 2, 3, 4};

                                Button btnTheLoai = alert.findViewById(R.id.btnTheLoai);

                                Button btnGia = alert.findViewById(R.id.btnGia);

                                Button btnSoLuong = alert.findViewById(R.id.btnSoLuong);

                                Button btnGioiTinh = alert.findViewById(R.id.btnGioiTinh);

                                mPriority = new ArrayList<>();

                                mPriorityList = new ArrayList<>();

                                mPriorityList.clear();

                                btnTheLoai.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Priority theLoai = new Priority(0, "");
                                        if (trangThai[0] == false) {
                                            onShowLayount(1, true);
                                            mPriority.add("theloai");
                                            mPriorityList.add(theLoai);
                                            viTri[0] = mPriority.size() - 1;
                                            trangThai[0] = true;
                                            btnTheLoai.setBackgroundTintList(getColorStateList(R.color.background));
                                            btnTheLoai.setText("Thể loại " + "(" + mPriority.size() + ")");
                                        } else if (trangThai[0] == true) {

                                            if (viTri[0] == (mPriority.size() - 1)) {
                                                onShowLayount(1, false);
                                                mPriority.remove("theloai");
                                                removeProproty(0);
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
                                        Priority giatien = new Priority(1, "");
                                        if (trangThai[1] == false) {
                                            onShowLayount(3, true);
                                            mPriority.add("giatien");
                                            mPriorityList.add(giatien);
                                            viTri[1] = mPriority.size() - 1;
                                            trangThai[1] = true;
                                            btnGia.setBackgroundTintList(getColorStateList(R.color.background));
                                            btnGia.setText("Giá tiền " + "(" + mPriority.size() + ")");
                                        } else if (trangThai[1] == true) {

                                            if (viTri[1] == (mPriority.size() - 1)) {
                                                onShowLayount(3, false);
                                                mPriority.remove("giatien");
                                                removeProproty(1);
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
                                        Priority soluong = new Priority(2, "");
                                        if (trangThai[2] == false) {
                                            onShowLayount(2, true);
                                            mPriority.add("soluong");
                                            mPriorityList.add(soluong);
                                            viTri[2] = mPriority.size() - 1;
                                            trangThai[2] = true;
                                            btnSoLuong.setBackgroundTintList(getColorStateList(R.color.background));
                                            btnSoLuong.setText("Số lượng " + "(" + mPriority.size() + ")");
                                        } else if (trangThai[2] == true) {

                                            if (viTri[2] == (mPriority.size() - 1)) {
                                                onShowLayount(2, false);
                                                mPriority.remove("soluong");
                                                removeProproty(2);
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
                                        Priority gioitinh = new Priority(3, "");
                                        if (trangThai[3] == false) {
                                            onShowLayount(4, true);
                                            mPriority.add("gioitinh");
                                            mPriorityList.add(gioitinh);
                                            viTri[3] = mPriority.size() - 1;
                                            trangThai[3] = true;
                                            btnGioiTinh.setBackgroundTintList(getColorStateList(R.color.background));
                                            btnGioiTinh.setText("Giới tính " + "(" + mPriority.size() + ")");
                                        } else if (trangThai[3] == true) {

                                            if (viTri[3] == (mPriority.size() - 1)) {
                                                onShowLayount(4, false);
                                                mPriority.remove("gioitinh");
                                                removeProproty(3);
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
                                builder.create().show();
                                break;
                        }
                        return false;
                    }

                });
                popupMenu.show();
            }
        });

        // the loai
        spnTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                addProproty(0, item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // gioi tinh
        btnNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProproty(3, btnNam.getText().toString());
            }
        });
        btnNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProproty(3, btnNu.getText().toString());
            }
        });
        btnTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProproty(3, btnTatCa.getText().toString());
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
                if (!s.toString().isEmpty()) {
                    addProproty(2, s.toString());
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
                        tv_maxTien.setText(seekBar.getProgress() * 500000 + "");
                    }
                });
                addProproty(1, String.valueOf(seekBar.getProgress() * 500000));
            }
        });

        // xac nhan
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPriorityList != null) {
                    if (checkVaildate(mPriorityList)) {
                        mPresenter.onClickFilter(mPriorityList, mListProduct);
                        Log.d("CheckHihi", new Gson().toJson(mPriorityList));
                    } else {
                        Log.d("CheckHihi", new Gson().toJson(mPriorityList));
                    }
                    //  bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
    }

    private boolean checkVaildate(List<Priority> mPriorityList) {
        boolean isStatus = true;
        for (int i = 0; i < mPriorityList.size(); i++) {
            if (mPriorityList.get(i).getId() == 0) {
                if (mPriorityList.get(i).getValue().isEmpty()) {
                    isStatus = false;
                    showMessage("Không bỏ trống thể loại");
                }
            } else if (mPriorityList.get(i).getId() == 1) {
                if (mPriorityList.get(i).getValue().isEmpty()) {
                    isStatus = false;
                    showMessage("Không bỏ trống giá tiền");
                }
            } else if (mPriorityList.get(i).getId() == 2) {
                if (mPriorityList.get(i).getValue().isEmpty()) {
                    isStatus = false;
                    showMessage("Không bỏ trống số lượng người");
                }
            } else if (mPriorityList.get(i).getId() == 3) {
                if (mPriorityList.get(i).getValue().isEmpty()) {
                    isStatus = false;
                    showMessage("Không bỏ trống giới tính");
                }
            }
        }

        return isStatus;
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

    private void onShowLayount(int positon, boolean isShow) {
        if (isShow) {
            switch (positon) {
                case 1:
                    spnTheLoai.setVisibility(View.VISIBLE);
                    ln_theloai.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    tv_songuoi.setVisibility(View.VISIBLE);
                    edt_amount_person.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    ln_chongia.setVisibility(View.VISIBLE);
                    tv_giaca.setVisibility(View.VISIBLE);
                    snb_price.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    tv_gioitinh.setVisibility(View.VISIBLE);
                    rgGender.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            switch (positon) {
                case 1:
                    spnTheLoai.setVisibility(View.GONE);
                    ln_theloai.setVisibility(View.GONE);
                    break;
                case 2:
                    tv_songuoi.setVisibility(View.GONE);
                    edt_amount_person.setVisibility(View.GONE);
                    break;
                case 3:
                    ln_chongia.setVisibility(View.GONE);
                    tv_giaca.setVisibility(View.GONE);
                    snb_price.setVisibility(View.GONE);
                    break;
                case 4:
                    tv_gioitinh.setVisibility(View.GONE);
                    rgGender.setVisibility(View.GONE);
                    break;
            }
        }

    }

    private void addProproty(int id, String value) {
        for (int i = 0; i < mPriorityList.size(); i++) {
            if (mPriorityList.get(i).getId() == id) {
                mPriorityList.get(i).setValue(value);
            }
        }
    }

    private void removeProproty(int id) {
        for (int i = 0; i < mPriorityList.size(); i++) {
            if (mPriorityList.get(i).getId() == id) {
                mPriorityList.remove(i);
            }
        }
    }


}