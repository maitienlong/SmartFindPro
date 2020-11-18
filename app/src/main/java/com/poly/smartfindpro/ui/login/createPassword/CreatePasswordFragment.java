package com.poly.smartfindpro.ui.login.createPassword;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentCreatePasswordBinding;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;

public class CreatePasswordFragment extends BaseDataBindFragment<FragmentCreatePasswordBinding, CreatePasswordPresenter> implements CreatePasswordContract.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_password;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter = new CreatePasswordPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
    }

    public void OnBackClick() {
        getBaseActivity().onBackFragment();
    }
}
