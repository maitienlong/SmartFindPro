package com.poly.smartfindpro.ui.login.loginFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.MainActivity;



public class LoginFragment extends BaseDataBindFragment<FragmentLoginBinding, LoginFragmentPresenter> implements LoginFragmentContract.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {

        mPresenter = new LoginFragmentPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);


    }

    @Override
    protected void initData() {

    }

    @Override
    public void saveLogin(String username, String password, String token) {
        getBaseActivity().showAlertDialog("Thông báo", "Bạn muốn ghi nhớ đăng nhập", "Có", "Không", true, new AlertDialogListener() {
            @Override
            public void onAccept() {
                showLoadingDialog();
                if (onSaveLogin(username, password, token)) {
                    Intent intent = new Intent(mActivity, MainActivity.class);
                    startActivity(intent);
                    mActivity.finish();
                }else {
                    showMessage("Lưu đăng nhập không thành công !");
                }

            }

            @Override
            public void onCancel() {
                showLoadingDialog();
                Intent intent = new Intent(mActivity, MainActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
        });
    }

    private boolean onSaveLogin(String username, String password, String token) {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences("smartFind", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("token", token);
        editor.putBoolean("savestatus", true);

        return editor.commit();
    }
}
