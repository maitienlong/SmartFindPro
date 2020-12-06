package com.poly.smartfindpro.ui.user.setting.information;

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
        mPresenter = new InforPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Thông tin cá nhân");

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }
}
