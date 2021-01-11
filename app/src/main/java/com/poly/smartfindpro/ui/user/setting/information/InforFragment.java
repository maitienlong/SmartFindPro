package com.poly.smartfindpro.ui.user.setting.information;

import androidx.activity.OnBackPressedCallback;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentInformationProfileBinding;
import com.poly.smartfindpro.databinding.FragmentInformationsBinding;
import com.poly.smartfindpro.databinding.FragmentSettingUserBinding;
import com.poly.smartfindpro.ui.user.setting.SettingPresenter;

public class InforFragment extends BaseDataBindFragment<FragmentInformationProfileBinding, InforPresenter> implements InforContact.ViewModel {


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
}
