package com.poly.smartfindpro.ui.user.setting.information;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.home.res.Address;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
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
        Bundle bundle = new Bundle();
        bundle.putString(Config.TOKEN_USER, Config.TOKEN_USER);
        Log.d("TAG", Config.TOKEN_USER);
        getBaseActivity().goToFragment(R.id.fl_native, new AddressFragment(), bundle);
    }
}