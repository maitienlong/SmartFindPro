package com.poly.smartfindpro.ui.user.setting.information;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.addressgoogle.Candidate;
import com.poly.smartfindpro.data.model.area.req.BodyReq;
import com.poly.smartfindpro.data.model.area.result.ListArea;
import com.poly.smartfindpro.data.model.area.result.ResultArea;
import com.poly.smartfindpro.data.model.post.req.Address;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.databinding.FragmentAddressBinding;
import com.poly.smartfindpro.ui.post.adapter.SpinnerAreaAdapter;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostPresenter;
import com.poly.smartfindpro.ui.post.adressPost.chooselocation.ChooseLoactionFragment;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesPostFragment;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddressFragment extends BaseDataBindFragment<FragmentAddressBinding, AddressPresenter> implements AddressContact.ViewModel {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_address;
    }

    @Override
    protected void initView() {
        mPresenter = new AddressPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Địa chỉ");

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        BodyReq proviceReq = new BodyReq("P", "");
        mPresenter.getDataApiArea(0, new Gson().toJson(proviceReq));

        mBinding.spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ListArea listArea = (ListArea) adapterView.getItemAtPosition(i);
                BodyReq bodyReq = new BodyReq("D", listArea.getAreaCode());
                mPresenter.getDataApiArea(1, new Gson().toJson(bodyReq));
                mPresenter.setP(listArea.getAreaName());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBinding.spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ListArea listArea = (ListArea) adapterView.getItemAtPosition(i);
                BodyReq bodyReq = new BodyReq("C", listArea.getAreaCode());
                mPresenter.getDataApiArea(2, new Gson().toJson(bodyReq));
                mPresenter.setD(listArea.getAreaName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBinding.spnComune.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListArea listArea = (ListArea) parent.getItemAtPosition(position);
                mPresenter.setC(listArea.getAreaName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    protected void initData() {

    }


    public void onShowProvince(ResultArea resultArea) {
        SpinnerAreaAdapter adapter = new SpinnerAreaAdapter(mActivity, resultArea.getListArea());

        mBinding.spnProvince.setAdapter(adapter);
    }

    public void onShowDistrict(ResultArea resultArea) {
        SpinnerAreaAdapter adapter = new SpinnerAreaAdapter(mActivity, resultArea.getListArea());

        mBinding.spnDistrict.setAdapter(adapter);
    }


    public void onShowCommune(ResultArea resultArea) {
        SpinnerAreaAdapter adapter = new SpinnerAreaAdapter(mActivity, resultArea.getListArea());

        mBinding.spnComune.setAdapter(adapter);
    }

    @Override
    public void onSubmitData(Address address, int status, List<Candidate> locationList) {

    }

    @Override
    public void onNext(String jsonData) {
        Intent intent = new Intent();
        intent.putExtra(Config.DATA_CALL_BACK, jsonData);

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }

}


