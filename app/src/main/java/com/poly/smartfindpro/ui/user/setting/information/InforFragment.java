package com.poly.smartfindpro.ui.user.setting.information;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.callback.OnFragmentCloseCallback;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.area.result.ResultArea;
import com.poly.smartfindpro.data.model.home.res.Address;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.model.updateaddress.AddressUpdate;
import com.poly.smartfindpro.databinding.FragmentInformationProfileBinding;
import com.poly.smartfindpro.databinding.FragmentInformationsBinding;
import com.poly.smartfindpro.databinding.FragmentSettingUserBinding;
import com.poly.smartfindpro.ui.user.setting.SettingPresenter;

import java.lang.reflect.Type;

public class InforFragment extends BaseDataBindFragment<FragmentInformationProfileBinding, InforPresenter> implements InforContact.ViewModel, OnFragmentCloseCallback {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information_profile;
    }

    @Override
    protected void initView() {
        mPresenter = new InforPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Thông tin cá nhân");

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }

    @Override
    public void onCLickUpdate() {

    }

    @Override
    public void onClickAddress() {

        getBaseActivity().goToFragment(R.id.fl_native, new AddressFragment(), null, this);
    }

    @Override
    public void onClose(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data.hasExtra(Config.DATA_CALL_BACK)) {
            Type type = new TypeToken<Address>() {
            }.getType();
            Address address = new Gson().fromJson(data.getStringExtra(Config.DATA_CALL_BACK), type);

            AddressUpdate addressUpdate = new AddressUpdate();
            addressUpdate.setDetailAddress(address.getDetailAddress());
            addressUpdate.setCommuneWardTown(address.getCommuneWardTown());
            addressUpdate.setDistrictsTowns(address.getDistrictsTowns());
            addressUpdate.setProvinceCity(address.getProvinceCity());

            mPresenter.setAddress(addressUpdate);

            mPresenter.setAddress(address.getDetailAddress() + ", "
                    + address.getCommuneWardTown() + ", "
                    + address.getDistrictsTowns() + ", "
                    + address.getProvinceCity());
        }
    }
}