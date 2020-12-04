package com.poly.smartfindpro.ui.login.loginFragment;

import android.view.View;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.MainActivity;
import com.poly.smartfindpro.ui.post.PostActivity;

public class LoginFragment extends BaseDataBindFragment<FragmentLoginBinding, LoginPresenter> implements LoginContract.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {

        mBinding.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().openActivity(MainActivity.class);
            }
        });

    }

    @Override
    protected void initData() {

    }
}
