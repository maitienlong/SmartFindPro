package com.poly.smartfindpro.ui.user.setting.information;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.model.post.req.Address;
import com.poly.smartfindpro.data.model.post.req.PostRequest;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.databinding.FragmentInformationProfileBinding;
import com.poly.smartfindpro.databinding.FragmentInformationsBinding;
import com.poly.smartfindpro.databinding.FragmentSettingUserBinding;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment;
import com.poly.smartfindpro.ui.user.setting.SettingPresenter;

import java.lang.reflect.Type;

public class InforFragment extends BaseDataBindFragment<FragmentInformationProfileBinding, InforPresenter> implements InforContact.ViewModel {

    private Address address;
    private ProfileResponse mProfile;


    Intent intent;
    String detail, commune, district, province;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information_profile;
    }

    @Override
    protected void initView() {
        mPresenter = new InforPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Thông tin cá nhân");

        address = new Address();
        mProfile = new ProfileResponse();
        intent = new Intent();


    }

    @Override
    protected void initData() {

//        if (intent != null) {
            detail = intent.getStringExtra("Detail");
            commune = intent.getStringExtra("Commune");
            district = intent.getStringExtra("District");
            province = intent.getStringExtra("Province");

            address.setDetailAddress(detail);
            address.setCommuneWardTown(commune);
            address.setDistrictsTowns(district);
            address.setProvinceCity(province);

            mPresenter.getInfor();


            mBinding.edtAddressProfile.setText(address.getDetailAddress() + ", " + address.getCommuneWardTown() + ", "
                    + address.getDistrictsTowns() + ", " + address.getProvinceCity());

//        } else {
//
//        }


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

        getBaseActivity().goToFragment(R.id.fl_native, new AddressFragment(), null);
    }

}
