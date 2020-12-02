package com.poly.smartfindpro.ui.user.setting;

import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentProfileBinding;
import com.poly.smartfindpro.databinding.FragmentSettingUserBinding;
import com.poly.smartfindpro.ui.user.setting.changepass.ChangePassFragment;
import com.poly.smartfindpro.ui.user.setting.information.InforFragment;
import com.poly.smartfindpro.ui.user.setting.permission.PermissionFragment;

public class SettingFragment extends BaseDataBindFragment<FragmentSettingUserBinding, SettingPresenter> implements SettingContact.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting_user;
    }

    @Override
    protected void initView() {
        mPresenter = new SettingPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Cài đặt");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }

    @Override
    public void onClickInformation() {
        getBaseActivity().goToFragment(R.id.fl_user, new InforFragment(), null);
    }

    @Override
    public void onClickChangePassword() {
        getBaseActivity().goToFragment(R.id.fl_user, new ChangePassFragment(), null);
    }

    @Override
    public void onClickPerMission() {
        getBaseActivity().goToFragment(R.id.fl_user, new PermissionFragment(), null);
    }
}
