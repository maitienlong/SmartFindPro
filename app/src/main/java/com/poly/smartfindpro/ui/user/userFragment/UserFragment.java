package com.poly.smartfindpro.ui.user.userFragment;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.activity.OnBackPressedCallback;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.ConfigSharedPreferences;
import com.poly.smartfindpro.databinding.FragmentSettingUserBinding;
import com.poly.smartfindpro.databinding.FragmentUserBinding;
import com.poly.smartfindpro.ui.login.LoginActivity;
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
        mPresenter = new UserPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void initData() {
        if (Config.isClick()) {
            mPresenter.setTextLogOut("Đăng xuất");
        } else {
            mPresenter.setTextLogOut("Đăng nhập");
        }
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
        if (Config.isClick()) {
            showAlertDialog("Thông báo", "Bạn muốn đăng xuất", "Đăng xuất", "Hủy", true, new AlertDialogListener() {
                @Override
                public void onAccept() {
                    mPresenter.onSigOut();
                }

                @Override
                public void onCancel() {

                }
            });
        } else {
            Config.TOKEN_USER = "";
            onSaveLogin("", "", "", 0, false);
            mActivity.finish();
            getBaseActivity().openActivity(LoginActivity.class);
        }


    }

    @Override
    public void onLogOut() {
        Config.TOKEN_USER = "";
        onSaveLogin("", "", "", 0, false);
        mActivity.finish();
        getBaseActivity().openActivity(LoginActivity.class);
    }

    private boolean onSaveLogin(String username, String password, String token, int level, boolean isSave) {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(Config.NAME_FILE_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(ConfigSharedPreferences.USERNAME, username);
        editor.putString(ConfigSharedPreferences.PASSWORD, password);
        editor.putString(ConfigSharedPreferences.TOKEN, token);
        editor.putInt(ConfigSharedPreferences.LEVEL, level);
        editor.putBoolean(ConfigSharedPreferences.IS_SAVE, isSave);

        return editor.commit();
    }
}
