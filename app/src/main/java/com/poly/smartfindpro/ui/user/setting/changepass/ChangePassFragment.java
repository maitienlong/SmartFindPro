package com.poly.smartfindpro.ui.user.setting.changepass;

import androidx.activity.OnBackPressedCallback;

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
        mPresenter = new ChangePassPresenter(mActivity,this, mBinding);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Đổi mật khẩu");

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
    public void onChangePass() {

    }
}