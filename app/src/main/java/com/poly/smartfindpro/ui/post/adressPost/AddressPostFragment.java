package com.poly.smartfindpro.ui.post.adressPost;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

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
import com.poly.smartfindpro.data.model.post.req.Address;
import com.poly.smartfindpro.data.model.post.req.PostRequest;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesPostFragment;

import java.lang.reflect.Type;

import static android.app.Activity.RESULT_OK;

public class AddressPostFragment extends BaseDataBindFragment<FragmentAddressPostBinding, AddressPostPresenter> implements AddressPostContract.ViewModel {
    private PostRequest postRequest;

    private String jsonPhoto;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_address_post;
    }

    private void ReciData() {
        Type type = new TypeToken<PostRequest>() {
        }.getType();

        postRequest = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), type);
    }

    @Override
    protected void initView() {

        ReciData();

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

    public void onSubmitData(Address address) {

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

        Log.d("CheckLog", new Gson().toJson(postRequest));

        Intent intent = new Intent();

        intent.putExtra(Config.DATA_CALL_BACK, "3");

        intent.putExtra(Config.POST_BUNDEL_RES, jsonData);

        intent.putExtra(Config.POST_BUNDEL_RES_PHOTO, getArguments().getString(Config.POST_BUNDEL_RES_PHOTO));


        setResult(RESULT_OK, intent);

        Bundle bundle = new Bundle();
        bundle.putString(Config.POST_BUNDEL_RES, jsonData);
        bundle.putString(Config.POST_BUNDEL_RES_PHOTO, getArguments().getString(Config.POST_BUNDEL_RES_PHOTO));

        onBackData();

        getBaseActivity().goToFragmentCallBackData(R.id.fl_post, new UtilitiesPostFragment(), bundle, getOnFragmentDataCallBack());

    }


}