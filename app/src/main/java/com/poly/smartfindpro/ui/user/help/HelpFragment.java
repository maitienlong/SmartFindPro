package com.poly.smartfindpro.ui.user.help;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentHelpUserBinding;
import com.poly.smartfindpro.databinding.FragmentProfileBinding;
import com.poly.smartfindpro.ui.user.setting.information.InforPresenter;

public class HelpFragment extends BaseDataBindFragment<FragmentHelpUserBinding, HelpPresenter> implements HelpContact.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_help_user;
    }

    @Override
    protected void initView() {
        mPresenter = new HelpPresenter(mActivity,this);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Trợ giúp và phản hồi");

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }
}
