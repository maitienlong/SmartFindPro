package com.poly.smartfindpro.ui.user.userFragment;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentSettingUserBinding;
import com.poly.smartfindpro.databinding.FragmentUserBinding;
import com.poly.smartfindpro.ui.user.help.HelpFragment;
import com.poly.smartfindpro.ui.user.profile.ProfileFragment;
import com.poly.smartfindpro.ui.user.rules.RulesFragment;
import com.poly.smartfindpro.ui.user.setting.SettingFragment;

public class UserFragment extends BaseDataBindFragment<FragmentUserBinding, UserPresenter> implements UserContact.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView() {
        mPresenter = new UserPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClickProfile() {
        getBaseActivity().goToFragment(R.id.fl_native, new ProfileFragment(), null);
    }

    @Override
    public void onClickSetting() {
        getBaseActivity().goToFragment(R.id.fl_native, new SettingFragment(), null);
    }

    @Override
    public void onClickRules() {
        getBaseActivity().goToFragment(R.id.fl_native, new RulesFragment(), null);
    }

    @Override
    public void onClickHelp() {
        getBaseActivity().goToFragment(R.id.fl_native, new HelpFragment(), null);
    }

    @Override
    public void onClickLogOut() {

    }
}
