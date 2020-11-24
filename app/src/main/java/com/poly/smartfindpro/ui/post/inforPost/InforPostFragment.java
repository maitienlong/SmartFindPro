package com.poly.smartfindpro.ui.post.inforPost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.base.BaseFragment;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentInforPostBinding;
import com.poly.smartfindpro.ui.MainActivity;
import com.poly.smartfindpro.ui.MainContract;
import com.poly.smartfindpro.ui.MainPresenter;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment;
import com.poly.smartfindpro.ui.post.model.Address;
import com.poly.smartfindpro.ui.post.model.InforModel;
import com.poly.smartfindpro.ui.post.model.Information;
import com.poly.smartfindpro.ui.post.model.PostRequest;

public class
InforPostFragment extends BaseDataBindFragment<FragmentInforPostBinding, InforPostPresenter>
        implements InforPostContract.ViewModel, View.OnTouchListener, View.OnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_infor_post;
    }

    String category;
    String mAmountPeople = "";
    String mPrice = "";
    String mDeposit = "";
    String mGender = "";
    String mAddress = "";
    String mElectricityBill = "";
    String mWaterBill = "";
    String mDescription = "";

    private PostRequest postRequest;
    private Information information;

    InforPostPresenter presenter;


    @Override
    protected void initView() {
        presenter = new InforPostPresenter(getContext(), this);

        //chon the loai
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

        postRequest = new PostRequest();
        information = new Information();

        information.setAmountPeople(Integer.valueOf(mAmountPeople));
        information.setPrice(Integer.valueOf(mPrice));
        information.setGender(mGender);
        information.setUnit("VNĐ");
        information.setDeposit(Integer.valueOf(mDeposit));
        information.setElectricBill(Integer.valueOf(mElectricityBill));
        information.setElectricUnit("Số");
        information.setWaterBill(Integer.valueOf(mWaterBill));
        information.setWaterUnit("Khối");
        information.setDescribe(mDescription);
        postRequest.setCategory(category);
        postRequest.setInformation(information);

        onNext(new Gson().toJson(postRequest));

    }

    @Override
    public void onErrorCategory() {
        Toast.makeText(getContext(), R.string.text_category_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorInfor() {
        Toast.makeText(getContext(), R.string.text_infor_error_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorGender() {
        Toast.makeText(getContext(), R.string.text_gender_empty, Toast.LENGTH_SHORT).show();
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

            //check The loai phong
            if (category == null) {
                onErrorCategory();
                return;
            }

            //gioi tinh
            if (mBinding.rbFemale.isChecked()) {
                mGender = mBinding.rbFemale.getText().toString();
            } else if (mBinding.rbMale.isChecked()) {
                mGender = mBinding.rbMale.getText().toString();
            } else if (mBinding.rbAll.isChecked()){
                mGender = mBinding.rbAll.getText().toString();
            }
            mElectricityBill = mBinding.edtElectricityBill.getText().toString();
            mWaterBill = mBinding.edtWaterBill.getText().toString();
            mDescription = mBinding.edtDescription.getText().toString();
            presenter.handleData(category, mAmountPeople, mPrice, mDeposit, mGender, mElectricityBill, mWaterBill, mDescription);

            ((PostActivity) getActivity())
                    .setDataInforModel(new InforModel(category, mAmountPeople,
                            mPrice, mDeposit, mGender, mAddress, mElectricityBill, mWaterBill, mDescription));
            ((PostActivity) getActivity()).setDataAddressModel(new Address());
        }
    }

    public void onNext(String jsonData){
        Fragment fragment = new AddressPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Config.POST_BUNDEL_RES, jsonData);
        FragmentTransaction fragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fl_post, fragment);
        fragmentTransaction.addToBackStack("addresspost");
        fragmentTransaction.commit();
    }
}
