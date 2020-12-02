package com.poly.smartfindpro.ui.user.setting.permission;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentUserPermissionBinding;
import com.poly.smartfindpro.ui.user.setting.information.InforPresenter;

public class PermissionFragment extends BaseDataBindFragment<FragmentUserPermissionBinding, PermissionPresenter>
implements PermissionContact.ViewModel{

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_permission;
    }

    @Override
    protected void initView() {
        mPresenter = new PermissionPresenter(mActivity,this);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Quyền truy cập");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }
}