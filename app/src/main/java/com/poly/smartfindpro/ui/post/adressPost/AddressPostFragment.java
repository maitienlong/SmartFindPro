package com.poly.smartfindpro.ui.post.adressPost;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.area.req.BodyReq;
import com.poly.smartfindpro.data.model.area.result.ListArea;
import com.poly.smartfindpro.data.model.area.result.ResultArea;
import com.poly.smartfindpro.databinding.FragmentAddressPostBinding;
import com.poly.smartfindpro.ui.post.adapter.SpinnerAreaAdapter;
import com.poly.smartfindpro.ui.post.model.Address;
import com.poly.smartfindpro.ui.post.model.PostRequest;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesPostFragment;

import java.lang.reflect.Type;

public class AddressPostFragment extends BaseDataBindFragment<FragmentAddressPostBinding, AddressPostPresenter> implements AddressPostContract.ViewModel {
    private PostRequest postRequest;

    private Address address;

    private String P, D, C;

    private String jsonPhoto;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_address_post;
    }

    private void ReciData() {
        Type type = new TypeToken<PostRequest>() {
        }.getType();

        postRequest = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), type);
        jsonPhoto = getArguments().getString(Config.POST_BUNDEL_RES_PHOTO);
    }

    @Override
    protected void initView() {

        ReciData();

        address = new Address();

        mPresenter = new AddressPostPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);

        BodyReq proviceReq = new BodyReq("P", "");
        mPresenter.getDataApiArea(0, new Gson().toJson(proviceReq));

        mBinding.spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ListArea listArea = (ListArea) adapterView.getItemAtPosition(i);
                BodyReq bodyReq = new BodyReq("D", listArea.getAreaCode());
                mPresenter.getDataApiArea(1, new Gson().toJson(bodyReq));
                P = listArea.getAreaName();
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

                D = listArea.getAreaName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBinding.spnComune.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListArea listArea = (ListArea) parent.getItemAtPosition(position);
                C = listArea.getAreaName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    public void onSubmitData() {
        address.setProvinceCity(P);
        address.setDistrictsTowns(D);
        address.setCommuneWardTown(C);
        address.setDetailAddress(mBinding.edtDetialAdress.getText().toString());
        postRequest.setAddress(address);
        onNext(new Gson().toJson(postRequest));
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

    public void onNext(String jsonData) {
        Fragment fragment = new UtilitiesPostFragment();

        Bundle bundle = new Bundle();

        bundle.putString(Config.POST_BUNDEL_RES, new Gson().toJson(postRequest));
        bundle.putString(Config.POST_BUNDEL_RES_PHOTO, jsonPhoto);

        FragmentTransaction fragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();

        fragment.setArguments(bundle);

        fragmentTransaction.add(R.id.fl_post, fragment);

        fragmentTransaction.addToBackStack("utilitiespost");

        fragmentTransaction.commit();

        Log.d("CheckLog", new Gson().toJson(postRequest));
    }


}