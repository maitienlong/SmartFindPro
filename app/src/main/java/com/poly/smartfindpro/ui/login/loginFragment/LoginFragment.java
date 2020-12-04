package com.poly.smartfindpro.ui.login.loginFragment;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.MainActivity;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.user.UserActivity;

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
                showLoadingDialog();
                Intent intent = new Intent(mActivity, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initData() {

    }
}
