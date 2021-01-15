package com.poly.smartfindpro.ui.user.rules;

import android.text.Html;

import androidx.activity.OnBackPressedCallback;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentUserRulesBinding;
import com.poly.smartfindpro.ui.user.profile.ProfilePresenter;


public class RulesFragment extends BaseDataBindFragment<FragmentUserRulesBinding, RulesPresenter> implements RulesContact.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_rules;
    }

    @Override
    protected void initView() {
        mPresenter = new RulesPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Điều Khoản");
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
//        mBinding.tvRule.setText(Html.fromHtml(getResources().getString(R.string.rule)).toString());
    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }
}
