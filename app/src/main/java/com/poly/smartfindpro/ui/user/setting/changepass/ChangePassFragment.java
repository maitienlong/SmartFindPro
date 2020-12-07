package com.poly.smartfindpro.ui.user.setting.changepass;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentChangePassBinding;
import com.poly.smartfindpro.ui.user.setting.information.InforPresenter;

public class ChangePassFragment extends BaseDataBindFragment<FragmentChangePassBinding, ChangePassPresenter>
        implements ChangePassContact.ViewModel {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_pass;
    }

    @Override
    protected void initView() {
        mPresenter = new ChangePassPresenter(mActivity,this);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Đổi mật khẩu");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }
}