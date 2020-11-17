package com.poly.smartfindpro.ui;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityMainBinding;
import com.poly.smartfindpro.ui.post.inforPost.InforPostPresenter;

public class MainActivity extends BaseDataBindActivity<ActivityMainBinding,
        MainPresenter> implements MainContract.ViewModel, View.OnTouchListener, View.OnClickListener {
    //
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
    String category;
    String mAmountPeople = "";
    String mPrice = "";
    String mDeposit = "";
    String mGender = "";
    String mElectricityBill = "";
    String mWaterBill = "";
    String mDescription = "";
    MainPresenter presenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        presenter = new MainPresenter(this);

        mBinding.btnNhaTro.setOnTouchListener(this);
        mBinding.btnOGhep.setOnTouchListener(this);
        mBinding.btnNguyenCan.setOnTouchListener(this);
        mBinding.btnChungCu.setOnTouchListener(this);
        mBinding.btnContinue.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onNextFragment() {
        Toast.makeText(this, category + "\n" + mGender + "\n" + mAmountPeople + "\n" +
                mPrice + "\n" + mDeposit + "\n" + mElectricityBill + "\n" + mWaterBill
                + "\n" + mDescription, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorCategory() {
        Toast.makeText(this, R.string.text_category_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorInfor() {
        Toast.makeText(this, R.string.text_infor_error_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorGender() {
        Toast.makeText(this, R.string.text_gender_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.btn_nha_tro:
                mBinding.btnNhaTro.setPressed(true);
                mBinding.btnChungCu.setPressed(false);
                mBinding.btnNguyenCan.setPressed(false);
                mBinding.btnOGhep.setPressed(false);
                category = mBinding.btnNhaTro.getText().toString();
                break;
            case R.id.btn_chung_cu:
                mBinding.btnNhaTro.setPressed(false);
                mBinding.btnChungCu.setPressed(true);
                mBinding.btnNguyenCan.setPressed(false);
                mBinding.btnOGhep.setPressed(false);
                category = mBinding.btnChungCu.getText().toString();
                break;
            case R.id.btn_nguyen_can:
                mBinding.btnNhaTro.setPressed(false);
                mBinding.btnChungCu.setPressed(false);
                mBinding.btnNguyenCan.setPressed(true);
                mBinding.btnOGhep.setPressed(false);
                category = mBinding.btnNguyenCan.getText().toString();
                break;

            case R.id.btn_o_ghep:
                mBinding.btnNhaTro.setPressed(false);
                mBinding.btnChungCu.setPressed(false);
                mBinding.btnNguyenCan.setPressed(false);
                mBinding.btnOGhep.setPressed(true);
                category = mBinding.btnOGhep.getText().toString();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnContinue) {
            mAmountPeople = mBinding.edtAmountPerson.getText().toString();
            mPrice = mBinding.edtPrice.getText().toString();
            mDeposit = mBinding.edtDeposit.getText().toString();
            int selectedGender = mBinding.rgGender.getCheckedRadioButtonId();
            RadioButton rb = findViewById(selectedGender);
            //check The loai phong
            if (category == null) {
                onErrorCategory();
                return;
            }

            //check gioi tinh
            if (rb == null) {
                onErrorGender();
                return;
            } else {
                mGender = rb.getText().toString();
            }


            mElectricityBill = mBinding.edtElectricityBill.getText().toString();
            mWaterBill = mBinding.edtWaterBill.getText().toString();
            mDescription = mBinding.edtDescription.getText().toString();
            presenter.handleData(category, mAmountPeople, mPrice, mDeposit, mGender, mElectricityBill, mWaterBill, mDescription);
        }
    }
}