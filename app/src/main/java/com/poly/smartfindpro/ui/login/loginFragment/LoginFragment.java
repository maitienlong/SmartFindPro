package com.poly.smartfindpro.ui.login.loginFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.ConfigSharedPreferences;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.MainActivity;


public class LoginFragment extends BaseDataBindFragment<FragmentLoginBinding, LoginFragmentPresenter> implements LoginFragmentContract.ViewModel {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {

        getData();
        mPresenter = new LoginFragmentPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);

    }

    private void getData() {
        if (getArguments() != null) {
            mBinding.edtAccountNumber.setText(getArguments().getString(Config.POST_BUNDEL_RES));
        }

    }

    @Override
    protected void initData() {
        initTokenFirebase();
    }

    @Override
    public void saveLogin(String username, String password, String token, int level) {

        Log.d("CheckSave", token);

        getBaseActivity().showAlertDialog("Thông báo", "Bạn muốn ghi nhớ đăng nhập", "Có", "Không", true, new AlertDialogListener() {
            @Override
            public void onAccept() {
                showLoadingDialog();
                if (onSaveLogin(username, password, token, level, true)) {
                    Intent intent = new Intent(mActivity, MainActivity.class);
                    startActivity(intent);
                    mActivity.finish();
                } else {
                    showMessage("Lưu đăng nhập không thành công !");
                }

            }

            @Override
            public void onCancel() {
                showLoadingDialog();
                if (onSaveLogin(username, password, token, level, false)) {
                    Intent intent = new Intent(mActivity, MainActivity.class);
                    startActivity(intent);
                    mActivity.finish();
                } else {
                    showMessage("Lưu đăng nhập không thành công !");
                }
            }
        });
//        }

    }

    @Override
    public void onShowDialog(String msg) {
        showAlertDialog("Thông báo", msg, "Đồng ý", "Từ chối", true, new AlertDialogListener() {
            @Override
            public void onAccept() {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public void onClickLogin() {

    }

    private boolean onSaveLogin(String username, String password, String token, int level, boolean isSave) {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(Config.NAME_FILE_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ConfigSharedPreferences.USERNAME, username);
        editor.putString(ConfigSharedPreferences.PASSWORD, password);
        editor.putString(ConfigSharedPreferences.TOKEN, token);
        editor.putInt(ConfigSharedPreferences.LEVEL, level);
        editor.putBoolean(ConfigSharedPreferences.IS_SAVE, isSave);
        editor.putString(ConfigSharedPreferences.TOKEN_DEVICE, Config.TOKEN_DEVICE);

        return editor.commit();
    }

    public void initTokenFirebase() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d("TOKEN", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        Config.TOKEN_DEVICE = task.getResult();
                        // Get new FCM registration token
                        Log.d("TOKEN", task.getResult());
                    }
                });

    }

}
